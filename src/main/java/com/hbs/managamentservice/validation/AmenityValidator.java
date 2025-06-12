package com.hbs.managamentservice.validation;

import com.hbs.managamentservice.exception.domain.amenity.AmenityAlreadyExistsException;
import com.hbs.managamentservice.exception.domain.amenity.AmenityInvalidIconException;
import com.hbs.managamentservice.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AmenityValidator {

    private final AmenityRepository amenityRepository;

    private static final List<String> ALLOWED_CONTENT_TYPES = List.of("image/png", "image/jpeg", "image/webp", "image/svg+xml");
    private static final long MAX_FILE_SIZE = 5L * 1024L * 1024L;

    public void validateNameIsUnique(String name) {
        if (amenityRepository.existsByName(name)) {
            throw new AmenityAlreadyExistsException();
        }
    }

    public void validateNameIsUniqueIfChanged(String newName, String currentName) {
        if (newName != null && !newName.equals(currentName)) {
            validateNameIsUnique(newName);
        }
    }

    public void validateIcon(MultipartFile icon) {
        if (icon == null || icon.isEmpty()) {
            throw new AmenityInvalidIconException("File cannot be empty");
        }

        if (!ALLOWED_CONTENT_TYPES.contains(icon.getContentType())) {
            throw new AmenityInvalidIconException("Invalid file type. Allowed types: PNG, JPEG, WEBP");
        }

        if (icon.getSize() > MAX_FILE_SIZE) {
            throw new AmenityInvalidIconException("Size of file cannot be greater than 5 MB");
        }
    }
}
