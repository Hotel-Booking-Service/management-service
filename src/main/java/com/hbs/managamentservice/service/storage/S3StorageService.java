package com.hbs.managamentservice.service.storage;

import com.hbs.managamentservice.exception.domain.storage.EmptyFileException;
import com.hbs.managamentservice.exception.domain.storage.FileNotFoundException;
import com.hbs.managamentservice.exception.domain.storage.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    private final S3Client s3Client;

    @Value("${storage.s3.bucket}")
    private String bucket;

    @Value("${storage.s3.path.delim}")
    private String s3PathDelim;

    @Override
    public String upload(MultipartFile file, String path) {
        if (file.isEmpty()) throw new EmptyFileException();

        String key = path + s3PathDelim + UUID.randomUUID();
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(), RequestBody.fromBytes(file.getBytes())
            );
        } catch (IOException e) {
            throw new StorageException("Failed to lay out the file in S3:" + file.getOriginalFilename(), e);
        }
        return key;
    }

    @Override
    public byte[] download(String s3Key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(s3Key)
                    .build();
            ResponseBytes<GetObjectResponse> responseBytes =
                    s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes());
            return responseBytes.asByteArray();
        } catch (S3Exception e) {
            if (e.statusCode() == 404) throw new FileNotFoundException();
            throw new StorageException("Error when downloading a file from S3: s3Key=" + s3Key, e);
        }
    }

    @Override
    public void delete(String s3Key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

}
