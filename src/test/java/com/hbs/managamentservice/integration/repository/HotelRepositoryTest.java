package com.hbs.managamentservice.integration.repository;

import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelStatus;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.model.Role;
import com.hbs.managamentservice.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void whenCreateHotel_thenPersisted() {
        Hotel hotel = createHotel();
        Hotel savedHotel = hotelRepository.save(hotel);
        assertNotNull(savedHotel.getId());
        assertEquals(hotel.getName(), savedHotel.getName());
    }

    @Test
    void whenFindById_thenReturnHotel() {
        Hotel hotel = hotelRepository.save(createHotel());
        Optional<Hotel> found = hotelRepository.findById(hotel.getId());
        assertTrue(found.isPresent());
        assertEquals(hotel.getId(), found.get().getId());
    }

    @Test
    void whenUpdateHotel_thenChangesPersisted() {
        Hotel hotel = hotelRepository.save(createHotel());
        hotel.setName("Updated Hotel Name");
        Hotel updated = hotelRepository.save(hotel);
        assertEquals("Updated Hotel Name", updated.getName());
    }

    @Test
    void whenDeleteHotel_thenRemoved() {
        Hotel hotel = hotelRepository.save(createHotel());
        hotelRepository.delete(hotel);
        assertFalse(hotelRepository.findById(hotel.getId()).isPresent());
    }

    private Hotel createHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("Sunrise Resort");
        hotel.setDescription("A beautiful seaside hotel.");
        hotel.setStars(4);
        hotel.setStatus(HotelStatus.PLANNED);
        hotel.setActive(true);

        Location location = new Location();
        location.setCountry("Country");
        location.setCity("City");
        location.setStreet("Main Street");
        location.setBuilding("123A");
        location.setZipCode("00000");

        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail(UUID.randomUUID() + "@mail.com"); // Ensure uniqueness
        manager.setPasswordHash("hashedpassword123");
        manager.setRole(Role.RECEPTIONIST);
        manager.setPhone("123456789");

        hotel.setLocation(location);
        hotel.setManager(manager);

        return hotel;
    }
}
