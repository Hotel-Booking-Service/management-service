package com.hbs.managamentservice.service.photo;

import com.hbs.managamentservice.dto.response.PhotoUploadResponse;
import com.hbs.managamentservice.exception.domain.hotel.PhotoNotFoundException;
import com.hbs.managamentservice.mapper.HotelPhotoMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelPhoto;
import com.hbs.managamentservice.repository.HotelPhotoRepository;
import com.hbs.managamentservice.resolver.HotelPhotoResolver;
import com.hbs.managamentservice.resolver.HotelResolver;
import com.hbs.managamentservice.service.storage.PresignedUrlProvider;
import com.hbs.managamentservice.service.storage.S3StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelPhotoServiceImpl implements HotelPhotoService {

    private final PresignedUrlProvider presignedUrlProvider;
    private final HotelPhotoRepository photoRepository;
    private final HotelPhotoMapper photoMapper;
    private final HotelResolver hotelResolver;
    private final HotelPhotoResolver photoResolver;
    private final S3StorageService s3Service;

    @Value("${storage.s3.url-expiration-seconds:900}")
    private int urlExpirationSeconds;

    private static final String PATH_HOTEL_PHOTOS = "photos/hotel";

    @Override
    @Transactional(readOnly = true)
    public URI generatePresignedURIForPhoto(Long photoId) {
        HotelPhoto photo = photoRepository.findById(photoId).orElseThrow(PhotoNotFoundException::new);

        return URI.create(presignedUrlProvider.generatePresignedUrl(
                photo.getS3Key(),
                Duration.ofSeconds(urlExpirationSeconds)).toString());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhotoUploadResponse> getHotelPhotos(Long hotelId) {
        hotelResolver.resolveById(hotelId);
        return photoRepository.findByHotelId(hotelId).stream()
                .map(photo -> photoMapper.toURI(photo.getId()))
                .toList();
    }

    @Override
    @Transactional
    public List<PhotoUploadResponse> createHotelPhotos(Long hotelId, List<MultipartFile> photos) {
        Hotel hotel = hotelResolver.resolveById(hotelId);

        List<PhotoUploadResponse> uris = photos.stream()
                .map(photo -> {
                    String s3Key = s3Service.upload(photo, PATH_HOTEL_PHOTOS);

                    HotelPhoto hotelPhoto = new HotelPhoto();
                    hotelPhoto.setHotel(hotel);
                    hotelPhoto.setS3Key(s3Key);
                    photoRepository.save(hotelPhoto);

                    return photoMapper.toURI(hotelPhoto.getId());
                })
                .toList();

        return uris;
    }

    @Override
    @Transactional
    public void deleteHotelPhoto(Long photoId) {
        HotelPhoto photo = photoResolver.resolveById(photoId);
        photoRepository.deleteById(photoId);
        s3Service.delete(photo.getS3Key());
    }
}
