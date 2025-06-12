package com.hbs.managamentservice.integration.aspect;

import com.hbs.managamentservice.aspect.StorageTransactionAspect;
import com.hbs.managamentservice.service.storage.S3StorageService;
import com.hbs.managamentservice.service.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test-no-docker")
@EnableAspectJAutoProxy
class StorageTransactionAspectTest {

    @MockitoBean
    private S3StorageService s3StorageService;

    @Autowired
    private StorageService storageService;

    @TestConfiguration
    static class Config {
        @Bean
        public StorageService storageService() {
            return new DummyStorageService();
        }

        @Bean
        public StorageTransactionAspect storageTransactionAspect(S3StorageService s3StorageService) {
            return new StorageTransactionAspect(s3StorageService);
        }
    }

    static class DummyStorageService implements StorageService {
        @Override
        public String upload(MultipartFile file, String folder) {
            return "test-key";
        }

        @Override
        public byte[] download(String s3Key) {
            return new byte[0];
        }

        @Override
        public void delete(String key) {
            // dummy
        }
    }

    @Test
    @Transactional
    void shouldDeleteUploadedFileOnTransactionRollback() {
        MultipartFile mockFile = new MockMultipartFile(
                "file",
                "filename.txt",
                "text/plain",
                "file content".getBytes()
        );

        String key = storageService.upload(mockFile, null);
        assertThat(key).isEqualTo("test-key");

        TestTransaction.flagForRollback();
        TestTransaction.end();

        verify(s3StorageService, timeout(500)).delete("test-key");
    }

    @Test
    @Transactional
    void shouldDeferDeleteUntilAfterCommit() {
        storageService.delete("delete-key");

        TestTransaction.flagForCommit();
        TestTransaction.end();

        verify(s3StorageService, timeout(500)).delete("delete-key");
    }
}
