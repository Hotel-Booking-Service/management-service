package com.hbs.managamentservice.service.storage;

import java.net.URL;
import java.time.Duration;

public interface PresignedUrlProvider {
    URL generatePresignedUrl(String s3Key, Duration duration);
}
