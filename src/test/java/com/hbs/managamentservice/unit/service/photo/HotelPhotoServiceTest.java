package com.hbs.managamentservice.unit.service.photo;

import com.hbs.managamentservice.exception.domain.hotel.PhotoNotFoundException;
import com.hbs.managamentservice.model.HotelPhoto;
import com.hbs.managamentservice.repository.HotelPhotoRepository;
import com.hbs.managamentservice.service.photo.HotelPhotoServiceImpl;
import com.hbs.managamentservice.service.storage.PresignedUrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelPhotoServiceTest {

    @Mock
    private HotelPhotoRepository hotelPhotoRepository;

    @Mock
    private PresignedUrlService presignedUrlService;

    @InjectMocks
    private HotelPhotoServiceImpl hotelPhotoService;

    @Test
    void generatePresignedURIForPhoto_shouldReturnCorrectURI() throws MalformedURLException {
        HotelPhoto photo = getHotelPhoto();
        when(hotelPhotoRepository.findById(1L)).thenReturn(Optional.of(photo));
        when(presignedUrlService.generatePresignedUrl(photo.getS3Key(), Duration.ZERO))
                .thenReturn(URI.create("https://dima-shagahod/daun.jpg").toURL());

        URI uri = hotelPhotoService.generatePresignedURIForPhoto(1L);
        assertNotNull(uri);
        assertEquals("https://dima-shagahod/daun.jpg", uri.toString());
    }

    @Test
    void generatePresignedURIForPhoto_shouldThrowNotFoundException() {
        when(hotelPhotoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PhotoNotFoundException.class, () -> hotelPhotoService.generatePresignedURIForPhoto(1L));
    }

    private static HotelPhoto getHotelPhoto() {
        HotelPhoto photo = new HotelPhoto();
        photo.setId(1L);
        photo.setS3Key("dima-dolboeb");
        photo.setCreatedAt(LocalDateTime.now());
        return photo;
    }
}
