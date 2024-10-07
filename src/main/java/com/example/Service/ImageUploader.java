package com.example.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploader {

    String uploadImage(MultipartFile multipartFile);

    List<String>  allFiles(String type);

    String presignedUrl(String fileName);
}
