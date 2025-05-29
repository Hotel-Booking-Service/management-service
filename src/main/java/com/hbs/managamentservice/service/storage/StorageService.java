package com.hbs.managamentservice.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file, String path);

    byte[] download(String s3Key);

    void delete(String s3Key);
}
