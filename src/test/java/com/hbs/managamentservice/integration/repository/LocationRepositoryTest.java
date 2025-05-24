package com.hbs.managamentservice.integration.repository;

import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void whenSaveLocation_thenPersisted() {
        Location location = createLocation();
        Location savedLocation = locationRepository.save(location);
        assertNotNull(savedLocation.getId());
        assertEquals(location.getCity(), savedLocation.getCity());
    }

    @Test
    void whenFindById_thenReturnLocation() {
        Location location = createLocation();
        Location savedLocation = locationRepository.save(location);
        assertTrue(locationRepository.findById(savedLocation.getId()).isPresent());
    }

    @Test
    void whenUpdateLocation_thenChangesPersisted() {
        Location location = locationRepository.save(createLocation());
        location.setCity("NewCity");
        location.setCountry("NewCountry");
        Location savedLocation = locationRepository.save(location);
        assertNotNull(savedLocation.getId());
        assertEquals(location.getCity(), savedLocation.getCity());
        assertEquals(location.getCountry(), savedLocation.getCountry());
    }

    @Test
    void whenDeleteLocation_thenDeleted() {
        Location location = locationRepository.save(createLocation());
        locationRepository.delete(location);
        assertFalse(locationRepository.findById(location.getId()).isPresent());
    }

    private Location createLocation() {
        Location location = new Location();
        location.setCountry("Russia");
        location.setCity("Kirovo-chlensk");
        location.setStreet("Dodo-pizza");
        location.setBuilding("Pizdorvan");
        location.setZipCode("1489");
        return location;
    }
}
