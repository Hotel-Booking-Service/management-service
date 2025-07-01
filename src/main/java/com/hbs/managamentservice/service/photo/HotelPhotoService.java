package com.hbs.managamentservice.service.photo;

import com.hbs.managamentservice.dto.response.PhotoUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

public interface HotelPhotoService {
    URI generatePresignedURIForPhoto(Long photoId);

    List<PhotoUploadResponse> getHotelPhotos(Long hotelId);

    List<PhotoUploadResponse> createHotelPhotos(Long hotelId, List<MultipartFile> photos);

    void deleteHotelPhoto(Long photoId);
}
