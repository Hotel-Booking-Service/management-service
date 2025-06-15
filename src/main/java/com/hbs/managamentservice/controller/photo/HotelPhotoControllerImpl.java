package com.hbs.managamentservice.controller.photo;

import com.hbs.managamentservice.dto.response.PhotoUploadResponse;
import com.hbs.managamentservice.service.photo.HotelPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HotelPhotoControllerImpl implements HotelPhotoController {

    private final HotelPhotoService hotelPhotoService;

    @Override
    @GetMapping("/hotels/photos/{photoId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<Void> getPhotoById(@PathVariable Long photoId) {
        URI presignedURI = hotelPhotoService.generatePresignedURIForPhoto(photoId);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(presignedURI).build();
    }

    @Override
    @GetMapping("/hotels/{id}/photos")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoUploadResponse> getHotelPhotos(@PathVariable Long id) {
        return hotelPhotoService.getHotelPhotos(id);
    }

    @Override
    @PostMapping(value = "/hotels/{id}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<PhotoUploadResponse> createHotelPhotos(@PathVariable Long id, @RequestParam List<MultipartFile> photos) {
        return hotelPhotoService.createHotelPhotos(id, photos);
    }

    @Override
    @DeleteMapping("/hotels/photos/{photoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhotoById(@PathVariable Long photoId) {
        hotelPhotoService.deleteHotelPhoto(photoId);
    }
}
