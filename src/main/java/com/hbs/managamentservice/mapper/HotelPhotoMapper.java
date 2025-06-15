package com.hbs.managamentservice.mapper;

import com.hbs.managamentservice.dto.response.PhotoUploadResponse;
import org.mapstruct.Mapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface HotelPhotoMapper {

    default PhotoUploadResponse toURI(Long photoId) {
        return new PhotoUploadResponse(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/hotels/photos/{photoId}")
                .buildAndExpand(photoId)
                .toUri());
    }
}
