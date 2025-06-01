package com.hbs.managamentservice.controller.photo;

import com.hbs.managamentservice.service.photo.HotelPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotels/photos")
public class HotelPhotoControllerImpl implements HotelPhotoController {

    private final HotelPhotoService hotelPhotoService;

    @Override
    @GetMapping("/{photoId}")
    public ResponseEntity<Void> getPhotoById(@PathVariable Long photoId) {
        URI presignedURI = hotelPhotoService.generatePresignedURIForPhoto(photoId);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(presignedURI).build();
    }
}
