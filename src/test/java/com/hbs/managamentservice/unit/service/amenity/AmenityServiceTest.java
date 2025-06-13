package com.hbs.managamentservice.unit.service.amenity;

import com.hbs.managamentservice.dto.request.CreateAmenityRequest;
import com.hbs.managamentservice.dto.request.UpdateAmenityRequest;
import com.hbs.managamentservice.dto.response.AmenityResponse;
import com.hbs.managamentservice.mapper.AmenityMapper;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.repository.AmenityRepository;
import com.hbs.managamentservice.resolver.AmenityResolver;
import com.hbs.managamentservice.service.amenity.AmenityServiceImpl;
import com.hbs.managamentservice.service.storage.S3StorageService;
import com.hbs.managamentservice.validation.AmenityValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmenityServiceTest {

    @Mock
    AmenityResolver amenityResolver;

    @Mock
    AmenityRepository amenityRepository;

    @Mock
    AmenityMapper amenityMapper;

    @Mock
    S3StorageService s3StorageService;

    @Mock
    AmenityValidator amenityValidator;

    @InjectMocks
    AmenityServiceImpl amenityService;

    @Test
    void getAmenity_shouldReturnMappedAmenity() {
        Amenity amenity = new Amenity(1L, "Wi-Fi", "url");
        AmenityResponse response = new AmenityResponse(1L, "Wi-Fi", "url");

        Mockito.when(amenityResolver.resolveById(1L)).thenReturn(amenity);
        Mockito.when(amenityMapper.toResponse(amenity)).thenReturn(response);

        AmenityResponse result = amenityService.getAmenity(1L);

        assertEquals(response, result);
    }

    @Test
    void getAmenities_shouldReturnAllMappedAmenities() {
        List<Amenity> amenities = List.of(new Amenity(1L, "Wi-Fi", "url"));
        AmenityResponse response = new AmenityResponse(1L, "Wi-Fi", "url");

        Mockito.when(amenityRepository.findAll()).thenReturn(amenities);
        Mockito.when(amenityMapper.toResponse(any())).thenReturn(response);

        List<AmenityResponse> result = amenityService.getAmenities();

        assertEquals(1, result.size());
        assertEquals("Wi-Fi", result.getFirst().name());
    }

    @Test
    void createAmenity_shouldValidateAndSave() {
        MockMultipartFile icon = new MockMultipartFile("icon", "wifi.jpg", "image/jpeg", "test".getBytes());
        CreateAmenityRequest request = new CreateAmenityRequest("Wi-Fi", icon);

        String iconUrl = "s3://icons/wifi.jpg";
        Amenity entity = new Amenity(1L, "Wi-Fi", iconUrl);
        AmenityResponse response = new AmenityResponse(1L, "Wi-Fi", iconUrl);

        when(s3StorageService.upload(icon, "icons/amenities")).thenReturn(iconUrl);
        when(amenityMapper.toEntity("Wi-Fi", iconUrl)).thenReturn(entity);
        when(amenityMapper.toResponse(entity)).thenReturn(response);

        AmenityResponse result = amenityService.createAmenity(request);

        verify(amenityValidator).validateNameIsUnique("Wi-Fi");
        verify(amenityRepository).save(entity);

        assertEquals(response, result);
    }

    @Test
    void updateAmenity_withNameAndIcon_shouldUpdateBoth() {
        Long id = 1L;
        Amenity amenity = new Amenity(id, "Old Name", "oldUrl");
        MockMultipartFile newIcon = new MockMultipartFile("icon", "new.jpg", "image/jpeg", "data".getBytes());
        UpdateAmenityRequest request = new UpdateAmenityRequest("New Name", newIcon);
        String newIconUrl = "s3://icons/new.jpg";
        AmenityResponse response = new AmenityResponse(id, "New Name", newIconUrl);

        when(amenityResolver.resolveById(id)).thenReturn(amenity);
        when(s3StorageService.upload(newIcon, "icons/amenities")).thenReturn(newIconUrl);
        when(amenityMapper.toResponse(amenity)).thenReturn(response);

        AmenityResponse result = amenityService.updateAmenity(id, request);

        verify(amenityValidator).validateNameIsUniqueIfChanged("New Name", "Old Name");
        verify(amenityValidator).validateIcon(newIcon);
        verify(s3StorageService).delete("oldUrl");
        verify(s3StorageService).upload(newIcon, "icons/amenities");
        verify(amenityMapper).updateAmenityFromPatch(amenity, request, newIconUrl);

        assertEquals(response, result);
    }

    @Test
    void updateAmenity_withOnlyName_shouldNotUploadIcon() {
        Long id = 1L;
        Amenity amenity = new Amenity(id, "Old", "url");
        UpdateAmenityRequest request = new UpdateAmenityRequest("New", null);
        AmenityResponse response = new AmenityResponse(id, "New", "url");

        when(amenityResolver.resolveById(id)).thenReturn(amenity);
        when(amenityMapper.toResponse(amenity)).thenReturn(response);

        AmenityResponse result = amenityService.updateAmenity(id, request);

        verify(amenityValidator).validateNameIsUniqueIfChanged("New", "Old");
        verify(s3StorageService, never()).delete(anyString());
        verify(s3StorageService, never()).upload(any(), anyString());
        verify(amenityMapper).updateAmenityFromPatch(amenity, request, null);

        assertEquals(response, result);
    }

    @Test
    void deleteAmenity_shouldDeleteFromS3AndRepo() {
        Amenity amenity = new Amenity(1L, "Wi-Fi", "url");

        when(amenityResolver.resolveById(1L)).thenReturn(amenity);

        amenityService.deleteAmenity(1L);

        verify(s3StorageService).delete("url");
        verify(amenityRepository).delete(amenity);
    }
}
