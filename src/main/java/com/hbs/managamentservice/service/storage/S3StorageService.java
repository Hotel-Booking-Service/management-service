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
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService, PresignedUrlProvider {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${spring.storage.s3.bucket}")
    private String bucket;

    @Value("${spring.storage.s3.path.delim}")
    private String s3PathDelim;

    @Override
    public URL generatePresignedUrlIfExists(String s3Key, Duration duration) {

        this.validateObjectExists(s3Key);

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(b -> b.bucket(bucket).key(s3Key))
                .signatureDuration(duration)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(presignRequest);

        return presignedGetObjectRequest.url();
    }

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
            throw new StorageException("Не удалось выложить файл в S3: " + file.getOriginalFilename(), e);
        }
        return key;
    }

    @Override
    public byte[] download(String s3Key) {

        this.validateObjectExists(s3Key);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .build();
        ResponseBytes<GetObjectResponse> responseBytes =
                s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes());
        return responseBytes.asByteArray();
    }

    @Override
    public void delete(String s3Key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    private void validateObjectExists(String s3Key) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(bucket).key(s3Key).build();
            s3Client.headObject(headObjectRequest);
        } catch (S3Exception e) {
            throw new FileNotFoundException(s3Key);
        }
    }
}
