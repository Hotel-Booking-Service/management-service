package com.hbs.managamentservice.validation;

import com.hbs.managamentservice.exception.domain.hotel.AmenityNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.HotelNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.LocationNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.ManagerNotFoundException;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.repository.AmenityRepository;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.repository.LocationRepository;
import com.hbs.managamentservice.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class HotelEntityFetcher {

    private final HotelRepository hotelRepository;
    private final LocationRepository locationRepository;
    private final AmenityRepository amenityRepository;
    private final ManagerRepository managerRepository;

    public Hotel fetchHotel(Long id) {
        return hotelRepository.findById(id).orElseThrow(HotelNotFoundException::new);
    }

    public Location fetchLocation(Long id) {
        return locationRepository.findById(id).orElseThrow(LocationNotFoundException::new);
    }

    public Manager fetchManager(Long id) {
        return managerRepository.findById(id).orElseThrow(ManagerNotFoundException::new);
    }

    public Set<Amenity> fetchAmenities(Set<Long> amenityIds) {
        List<Amenity> amenities = amenityRepository.findAllById(amenityIds);
        if(amenities.size() != amenityIds.size()) {
            throw new AmenityNotFoundException();
        }
        return new HashSet<>(amenities);
    }
}
