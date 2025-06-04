package com.hbs.managamentservice.unit.validation;

import com.hbs.managamentservice.exception.domain.hotel.AmenityNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.HotelNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.LocationNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.ManagerNotFoundException;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelStatus;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.repository.AmenityRepository;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.repository.LocationRepository;
import com.hbs.managamentservice.repository.ManagerRepository;
import com.hbs.managamentservice.validation.HotelEntityFetcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HotelEntityFetcherTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private AmenityRepository amenityRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private HotelEntityFetcher hotelEntityFetcher;

    @Test
    void testFetchHotel_shouldReturnHotelEntity() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Hotel");
        hotel.setDescription("Hotel description");
        hotel.setStatus(HotelStatus.ACTIVE);
        hotel.setStars(5);
        hotel.setCreatedAt(LocalDateTime.now());
        hotel.setUpdatedAt(LocalDateTime.now());

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        Hotel hotelResponse = hotelEntityFetcher.fetchHotel(hotel.getId());
        assertNotNull(hotelResponse);
        assertEquals(hotel.getId(), hotelResponse.getId());
        assertEquals(hotel.getName(), hotelResponse.getName());
    }

    @Test
    void testFetchHotel_shouldThrowExceptionHotelNotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(HotelNotFoundException.class, () -> hotelEntityFetcher.fetchHotel(1L));
    }

    @Test
    void testFetchLocation_shouldReturnHotelLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setCountry("Dima");
        location.setCity("Shagahod");
        location.setStreet("Daun");
        location.setBuilding("Ebanniy");
        location.setZipCode("1488");

        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        Location locationResponse = hotelEntityFetcher.fetchLocation(location.getId());
        assertNotNull(locationResponse);
        assertEquals(location.getId(), locationResponse.getId());
        assertEquals(location.getBuilding(), locationResponse.getBuilding());
    }

    @Test
    void testFetchLocation_shouldThrowExceptionLocationNotFound() {
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(LocationNotFoundException.class, () -> hotelEntityFetcher.fetchLocation(1L));
    }

    @Test
    void testFetchHotelManager_shouldReturnHotelManager() {
        Manager manager = new Manager();
        manager.setId(1L);

        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));
        Manager managerResponse = hotelEntityFetcher.fetchManager(manager.getId());
        assertNotNull(managerResponse);
        assertEquals(manager.getId(), managerResponse.getId());

    }

    @Test
    void testFetchHotelManager_shouldThrowExceptionManagerNotFound() {
        when(managerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ManagerNotFoundException.class, () -> hotelEntityFetcher.fetchManager(1L));
    }

    @Test
    void testFetchHotelAmenities_shouldReturnHotelAmenities() {
        Set<Long> amenityIds = new HashSet<>(List.of(1L));
        Amenity amenity = new Amenity();
        amenity.setId(1L);

        when(amenityRepository.findById(1L)).thenReturn(Optional.of(amenity));

        Set<Amenity> amenities = hotelEntityFetcher.fetchAmenities(amenityIds);
        assertNotNull(amenities);
        assertEquals(amenityIds.size(), amenities.size());
    }

    @Test
    void testFetchHotelAmenities_shouldThrowExceptionAmenitiesNotFound() {
        Set<Long> amenityIds = new HashSet<>(List.of(1L));
        when(amenityRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AmenityNotFoundException.class, () -> hotelEntityFetcher.fetchAmenities(amenityIds));
    }
}
