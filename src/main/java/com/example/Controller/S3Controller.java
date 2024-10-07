package com.example.Controller;

import com.example.Service.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/aws/api/v1/s3")
public class S3Controller {

    @Autowired
    private ImageUploader imageUploader;



    @PostMapping("/posts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> uploadImage(@RequestPart MultipartFile file){
        if (file.isEmpty()) {
            return new ResponseEntity<>("File is missing", HttpStatus.BAD_REQUEST);
        }
        String uploadimage = imageUploader.uploadImage(file);
        return new ResponseEntity<>(uploadimage, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<String> allFiles(@RequestParam String type){
        List<String> strings = imageUploader.allFiles(type);
        return strings;
    }
}
