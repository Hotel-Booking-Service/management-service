package com.hbs.managamentservice.service.storage;

import java.net.URL;
import java.time.Duration;

public interface PresignedUrlProvider {
    URL generatePresignedUrlIfExists(String s3Key, Duration duration);
}
