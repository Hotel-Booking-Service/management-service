package com.hbs.managamentservice.service.hotel;

import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.validation.HotelEntityFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HotelRelationResolver {

    private final HotelEntityFetcher hotelFetcher;

    public void resolveRelations(Hotel hotel, UpdateHotelRequest request) {
        Optional.ofNullable(request.getLocationId())
                .ifPresent(locationId -> hotel.setLocation(hotelFetcher.fetchLocation(locationId)));

        Optional.ofNullable(request.getManagerId())
                .ifPresent(managerId-> hotel.setManager(hotelFetcher.fetchManager(managerId)));

        Optional.ofNullable(request.getAmenityIds())
                .ifPresent(amenityIds-> hotel.setAmenities(hotelFetcher.fetchAmenities(amenityIds)));
    }
}
