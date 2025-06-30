package com.hbs.managamentservice.service.amenity;

import com.hbs.managamentservice.dto.request.CreateAmenityRequest;
import com.hbs.managamentservice.dto.request.UpdateAmenityRequest;
import com.hbs.managamentservice.dto.response.AmenityResponse;
import com.hbs.managamentservice.mapper.AmenityMapper;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.repository.AmenityRepository;
import com.hbs.managamentservice.resolver.AmenityResolver;
import com.hbs.managamentservice.service.storage.S3StorageService;
import com.hbs.managamentservice.validation.AmenityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityResolver amenityResolver;
    private final AmenityRepository amenityRepository;
    private final AmenityMapper amenityMapper;
    private final S3StorageService s3StorageService;
    private final AmenityValidator amenityValidator;

    private static final String AMENITY_ICON_FOLDER = "icons/amenities";

    @Override
    @Transactional(readOnly = true)
    public AmenityResponse getAmenity(Long id) {
        Amenity amenity = amenityResolver.resolveById(id);
        return amenityMapper.toResponse(amenity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AmenityResponse> getAmenities() {
        List<Amenity> amenities = amenityRepository.findAll();
        return amenities.stream()
                .map(amenityMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public AmenityResponse createAmenity(CreateAmenityRequest request) {
        amenityValidator.validateNameIsUnique(request.getName());

        String iconUrl = s3StorageService.upload(request.getIcon(), AMENITY_ICON_FOLDER);

        Amenity amenity = amenityMapper.toEntity(request.getName(), iconUrl);
        amenityRepository.save(amenity);
        return amenityMapper.toResponse(amenity);
    }

    @Override
    @Transactional
    public AmenityResponse updateAmenity(Long id, UpdateAmenityRequest request) {
        Amenity amenity = amenityResolver.resolveById(id);

        if (request.getName() != null && !request.getName().isEmpty()) {
            amenityValidator.validateNameIsUniqueIfChanged(request.getName(), amenity.getName());
        }

        String iconUrl = null;
        if (request.getIcon() != null && !request.getIcon().isEmpty()) {
            amenityValidator.validateIcon(request.getIcon());
            s3StorageService.delete(amenity.getIcon());
            iconUrl = s3StorageService.upload(request.getIcon(), AMENITY_ICON_FOLDER);
        }

        amenityMapper.updateAmenityFromPatch(amenity, request, iconUrl);
        return amenityMapper.toResponse(amenity);
    }

    @Override
    @Transactional
    public void deleteAmenity(Long id) {
        Amenity amenity = amenityResolver.resolveById(id);
        s3StorageService.delete(amenity.getIcon());
        amenityRepository.delete(amenity);
    }
}
