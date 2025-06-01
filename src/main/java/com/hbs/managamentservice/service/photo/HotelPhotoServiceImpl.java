package com.hbs.managamentservice.service.photo;

import com.hbs.managamentservice.exception.domain.hotel.PhotoNotFoundException;
import com.hbs.managamentservice.model.HotelPhoto;
import com.hbs.managamentservice.repository.HotelPhotoRepository;
import com.hbs.managamentservice.service.storage.PresignedUrlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class HotelPhotoServiceImpl implements HotelPhotoService{

    private final PresignedUrlProvider presignedUrlProvider;
    private final HotelPhotoRepository photoRepository;

    @Value("${storage.s3.url-expiration-seconds:900}")
    private int urlExpirationSeconds;

    @Override
    @Transactional(readOnly = true)
    public URI generatePresignedURIForPhoto(Long photoId) {
        HotelPhoto photo = photoRepository.findById(photoId).orElseThrow(PhotoNotFoundException::new);

        return URI.create(presignedUrlProvider.generatePresignedUrl(
                photo.getS3Key(),
                Duration.ofSeconds(urlExpirationSeconds)).toString());
    }
}
