package unit.storage;

import com.hbs.managamentservice.service.storage.PresignedUrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PresignedUrlServiceTest {

    @Mock
    private S3Presigner s3Presigner;

    @InjectMocks
    private PresignedUrlService presignedUrlService;

    @BeforeEach
    void setUp() throws Exception {
        Field bucketField = PresignedUrlService.class.getDeclaredField("bucket");
        bucketField.setAccessible(true);
        bucketField.set(presignedUrlService, "test-bucket");
    }

    @Test
    void generatePresignedUrlIfExists_shouldReturnUrl_whenObjectExists() throws MalformedURLException {
        String s3Key = "some/key/file.txt";
        URL expectedUrl = URI.create("https://example.com/presigned-url").toURL();

        PresignedGetObjectRequest presignedRequest = mock(PresignedGetObjectRequest.class);
        when(presignedRequest.url()).thenReturn(expectedUrl);

        when(s3Presigner.presignGetObject(any(GetObjectPresignRequest.class)))
                .thenReturn(presignedRequest);

        URL actualUrl = presignedUrlService.generatePresignedUrlIfExists(s3Key, Duration.ofMinutes(15));

        assertEquals(expectedUrl, actualUrl);
        verify(s3Presigner).presignGetObject(any(GetObjectPresignRequest.class));
        verify(presignedRequest).url();
    }
}
