package com.example.ServiceImpl;

import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.example.Exception.ImageUploadException;
import com.example.Service.ImageUploader;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class S3ImageUploaderServiceImpl  implements ImageUploader {

    @Autowired
    private AmazonS3 client;

    @Value("${cloud.s3.bucket}")
    private String bucket;


    private static final List<String> IMAGE_EXTENSIONS = List.of(".png", ".jpg", ".jpeg", ".gif");
    private static final List<String> VIDEO_EXTENSIONS = List.of(".mp4", ".webm");

    @Override
    public String uploadImage(MultipartFile image) {
        //actualFileName=abc.png
        String actualFileName=image.getOriginalFilename();


        String fileName = UUID.randomUUID().toString() + actualFileName.substring(actualFileName.lastIndexOf("."));

        //creating a metaData
        ObjectMetadata  metadata= new ObjectMetadata();
        metadata.setContentLength(image.getSize());

        try {
            PutObjectResult putObjectResult = client.putObject(new PutObjectRequest(bucket,fileName,image.getInputStream(),metadata));
            String imageUrl = this.presignedUrl(fileName);
            return imageUrl;
        } catch (IOException e) {
            throw new ImageUploadException("error in uploading image"+e.getMessage());
        }
    }

    @Override
    public List<String> allFiles(String type) {

        if (!type.equals("image") && !type.equals("video")) {
            throw new IllegalArgumentException("Invalid type. Must be 'image' or 'video'.");
        }
        //2nd do that
        ListObjectsV2Request listObjectRequest=new ListObjectsV2Request().withBucketName(bucket);

//1st do that
        ListObjectsV2Result listObjectsV2Result = client.listObjectsV2(listObjectRequest);
        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();
        List<String> list = objectSummaries.stream().map(item -> this.presignedUrl(item.getKey())).toList();

        //filter image and vedio
        List<String> images =null;
        List<String> videos=null;
        if (type.equals("image")) {
            images = filterImages(list);
            return images;
        } else if (type.equals("video")) {
            videos = filterVideos(list);
            return videos;
        }
        return list;
    }

    @Override
    public String presignedUrl(String fileName) {

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTHEAST_2)
                .build();

        Date expirationDate = new Date();
        long time = expirationDate.getTime();
        int days = 7; // 10 years
        time += days * 24 * 60 * 60 * 1000;
        expirationDate.setTime(time);
        System.out.println(expirationDate);

        //here we are passing the expire date
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket,fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expirationDate)
                ;

//getting a url of image or vedio
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

    public List<String> filterImages(List<String> urls) {
        return urls.stream()
                .filter(url -> {
                    String fileName = extractFileNameFromUrl(url);
                    return IMAGE_EXTENSIONS.stream().anyMatch(fileName::endsWith);
                })
                .collect(Collectors.toList());
    }

    public List<String> filterVideos(List<String> urls) {
        return urls.stream()
                .filter(url -> {
                    String fileName = extractFileNameFromUrl(url);
                    return VIDEO_EXTENSIONS.stream().anyMatch(fileName::endsWith);
                })
                .collect(Collectors.toList());
    }

    private String extractFileNameFromUrl(String url) {
        try {
            URL urlObj = new URL(url);
            String path = urlObj.getPath();
            return URLDecoder.decode(path.substring(path.lastIndexOf("/") + 1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Handle potential exceptions, e.g., URL format issues
            return "";
        }
    }
}

