package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class HotelRelationResolver {

    private final LocationResolver locationResolver;
    private final ManagerResolver managerResolver;
    private final AmenityResolver amenityResolver;

    public void resolveRelations(UpdateHotelRequest request, Hotel hotel) {
        if (request.getLocationId() != null) {
            Location location = locationResolver.resolveById(request.getLocationId());
            hotel.setLocation(location);
        }
        if (request.getManagerId() != null) {
            Manager manager = managerResolver.resolveById(request.getManagerId());
            hotel.setManager(manager);
        }
        if (request.getAmenityIds() != null) {
            Set<Amenity> amenities = amenityResolver.resolveByIds(request.getAmenityIds());
            hotel.setAmenities(amenities);
        }
    }
}
