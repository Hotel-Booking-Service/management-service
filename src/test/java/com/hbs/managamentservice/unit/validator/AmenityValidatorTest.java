package com.hbs.managamentservice.unit.validator;

import com.hbs.managamentservice.exception.domain.amenity.AmenityAlreadyExistsException;
import com.hbs.managamentservice.exception.domain.amenity.AmenityInvalidIconException;
import com.hbs.managamentservice.repository.AmenityRepository;
import com.hbs.managamentservice.validation.AmenityValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmenityValidatorTest {

    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private AmenityValidator amenityValidator;

    @Test
    void validateNameIsUnique_shouldPass_whenNameDoesNotExist() {
        when(amenityRepository.existsByName("Pool")).thenReturn(false);

        assertDoesNotThrow(() -> amenityValidator.validateNameIsUnique("Pool"));
    }

    @Test
    void validateNameIsUnique_shouldThrow_whenNameExists() {
        when(amenityRepository.existsByName("Pool")).thenReturn(true);

        assertThrows(AmenityAlreadyExistsException.class, () ->
                amenityValidator.validateNameIsUnique("Pool"));
    }

    @Test
    void validateNameIsUniqueIfChanged_shouldPass_whenNamesAreEqual() {
        assertDoesNotThrow(() ->
                amenityValidator.validateNameIsUniqueIfChanged("Pool", "Pool"));

        verifyNoInteractions(amenityRepository);
    }

    @Test
    void validateNameIsUniqueIfChanged_shouldPass_whenNewNameIsNull() {
        assertDoesNotThrow(() ->
                amenityValidator.validateNameIsUniqueIfChanged(null, "Pool"));

        verifyNoInteractions(amenityRepository);
    }

    @Test
    void validateNameIsUniqueIfChanged_shouldThrow_whenNewNameExists() {
        when(amenityRepository.existsByName("Gym")).thenReturn(true);

        assertThrows(AmenityAlreadyExistsException.class, () ->
                amenityValidator.validateNameIsUniqueIfChanged("Gym", "Pool"));
    }

    @Test
    void validateNameIsUniqueIfChanged_shouldPass_whenNewNameIsUnique() {
        when(amenityRepository.existsByName("Gym")).thenReturn(false);

        assertDoesNotThrow(() ->
                amenityValidator.validateNameIsUniqueIfChanged("Gym", "Pool"));
    }

    @Test
    void validateIcon_shouldThrow_whenIconIsNull() {
        assertThrows(AmenityInvalidIconException.class, () ->
                amenityValidator.validateIcon(null));
    }

    @Test
    void validateIcon_shouldThrow_whenIconIsEmpty() {
        MultipartFile emptyFile = mock(MultipartFile.class);
        when(emptyFile.isEmpty()).thenReturn(true);

        assertThrows(AmenityInvalidIconException.class, () ->
                amenityValidator.validateIcon(emptyFile));
    }

    @Test
    void validateIcon_shouldThrow_whenContentTypeInvalid() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("application/pdf");

        assertThrows(AmenityInvalidIconException.class, () ->
                amenityValidator.validateIcon(file));
    }

    @Test
    void validateIcon_shouldThrow_whenFileTooLarge() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(6L * 1024 * 1024); // 6 MB

        assertThrows(AmenityInvalidIconException.class, () ->
                amenityValidator.validateIcon(file));
    }

    @Test
    void validateIcon_shouldPass_whenFileIsValid() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(2L * 1024 * 1024); // 2 MB

        assertDoesNotThrow(() -> amenityValidator.validateIcon(file));
    }
}
