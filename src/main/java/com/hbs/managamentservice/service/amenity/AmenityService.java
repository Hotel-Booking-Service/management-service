package com.hbs.managamentservice.service.amenity;

import com.hbs.managamentservice.dto.request.CreateAmenityRequest;
import com.hbs.managamentservice.dto.request.UpdateAmenityRequest;
import com.hbs.managamentservice.dto.response.AmenityResponse;

import java.util.List;

public interface AmenityService {

    AmenityResponse getAmenity(Long id);

    List<AmenityResponse> getAmenities();

    AmenityResponse createAmenity(CreateAmenityRequest request);

    AmenityResponse updateAmenity(Long id, UpdateAmenityRequest request);

    void deleteAmenity(Long id);
}
