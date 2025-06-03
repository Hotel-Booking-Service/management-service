package com.hbs.managamentservice.validation;

import com.hbs.managamentservice.exception.domain.hotel.AmenityNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.HotelNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.LocationNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.ManagerNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.PhotoNotFoundException;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelPhoto;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.repository.AmenityRepository;
import com.hbs.managamentservice.repository.HotelPhotoRepository;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.repository.LocationRepository;
import com.hbs.managamentservice.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HotelEntityFetcher {

    private final HotelRepository hotelRepository;
    private final LocationRepository locationRepository;
    private final AmenityRepository amenityRepository;
    private final HotelPhotoRepository hotelPhotoRepository;
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
        return amenityIds.stream()
                .map(amenityId -> amenityRepository.findById(amenityId).orElseThrow(AmenityNotFoundException::new))
                .collect(Collectors.toSet());
    }

    public Set<HotelPhoto> fetchPhotos(Set<Long> photoIds) {
        return photoIds.stream()
                .map(photoId -> hotelPhotoRepository.findById(photoId).orElseThrow(PhotoNotFoundException::new))
                .collect(Collectors.toSet());
    }
}
