package com.clonecode.inssagram.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 s3;
//    private final S3Directory s3Directory;

    public List<String> uploadFile (List<MultipartFile> multipartFiles, String s3Directory) {
        List<String> imageUrlList = new ArrayList<>();
        for (MultipartFile file: multipartFiles) {
            String fileName=System.currentTimeMillis()+file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            try (InputStream inputStream = file.getInputStream()){
                s3.putObject(new PutObjectRequest(bucket, s3Directory+fileName, inputStream, objectMetadata));
                imageUrlList.add(s3.getUrl(bucket, s3Directory+fileName).toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imageUrlList;
    }
}
