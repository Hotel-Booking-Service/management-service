package com.hbs.managamentservice.mapper;

import com.hbs.managamentservice.dto.request.UpdateAmenityRequest;
import com.hbs.managamentservice.dto.response.AmenityResponse;
import com.hbs.managamentservice.model.Amenity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.StringUtils;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AmenityMapper {

    AmenityResponse toResponse(Amenity amenity);

    @Mapping(target = "icon", source = "iconUrl")
    Amenity toEntity(String name, String iconUrl);

    default void updateAmenityFromPatch(Amenity amenity, UpdateAmenityRequest request, String newIconUrl) {
        if (StringUtils.hasText(request.getName())) {
            amenity.setName(request.getName());
        }
        if (newIconUrl != null) {
            amenity.setIcon(newIconUrl);
        }
    }
}
