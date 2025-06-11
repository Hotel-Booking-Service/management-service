package com.hbs.managamentservice.mapper;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelPhoto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING)
public interface HotelMapper {


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateHotelFromPatchRequest(UpdateHotelRequest request, @MappingTarget Hotel hotel);

    Hotel toEntity(CreateHotelRequest createHotelRequest);

    @Mapping(target = "photos", source = "photos", qualifiedByName = "photosToUris")
    HotelResponse toHotelResponse(Hotel hotel);

    @Named("photosToUris")
    default List<URI> photosToUris(Set<HotelPhoto> photos) {
        if (photos == null) {
            return Collections.emptyList();
        }
        return photos.stream()
                .map(photo -> ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/v1/hotels/photos/{id}")
                        .buildAndExpand(photo.getS3Key())
                        .toUri())
                .toList();
    }
}