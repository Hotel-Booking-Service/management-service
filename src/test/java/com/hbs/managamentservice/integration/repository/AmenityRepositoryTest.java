package com.hbs.managamentservice.integration.repository;

import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.repository.AmenityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class AmenityRepositoryTest {

    @Autowired
    private AmenityRepository amenityRepository;

    @Test
    void whenCreateAmenity_thenPersisted() {
        Amenity amenity = createAmenity();
        Amenity savedAmenity = amenityRepository.save(amenity);
        assertNotNull(savedAmenity.getId());
        assertEquals(amenity.getName(), savedAmenity.getName());
    }

    @Test
    void whenFindById_thenReturnAmenity() {
        Amenity amenity = amenityRepository.save(createAmenity());
        assertNotNull(amenityRepository.findById(amenity.getId()));
    }

    @Test
    void whenUpdateAmenity_thenChangesPersisted() {
        Amenity amenity = amenityRepository.save(createAmenity());
        amenity.setName("UpdatedName");
        Amenity savedAmenity = amenityRepository.save(amenity);
        assertNotNull(savedAmenity.getId());
        assertEquals(amenity.getName(), savedAmenity.getName());
    }

    @Test
    void whenDeleteAmenity_thenDeleted() {
        Amenity amenity = amenityRepository.save(createAmenity());
        amenityRepository.delete(amenity);
        assertFalse(amenityRepository.findById(amenity.getId()).isPresent());
    }

    private Amenity createAmenity() {
        Amenity amenity = new Amenity();
        amenity.setName("WIFI");
        amenity.setIcon("https://шагихаметов.рф/");
        return amenity;
    }
}
