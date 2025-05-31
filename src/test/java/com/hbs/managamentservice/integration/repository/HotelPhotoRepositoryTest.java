package com.hbs.managamentservice.integration.repository;

import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelPhoto;
import com.hbs.managamentservice.model.HotelStatus;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.model.Role;
import com.hbs.managamentservice.repository.HotelPhotoRepository;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.repository.LocationRepository;
import com.hbs.managamentservice.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class HotelPhotoRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelPhotoRepository hotelPhotoRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Test
    void whenCreateHotelWithPhotos_thenPersisted() {
        Hotel hotel = hotelRepository.save(createHotel());

        HotelPhoto photo1 = new HotelPhoto();
        photo1.setS3Key("https://example.com/photo1.jpg");
        photo1.setHotel(hotel);

        HotelPhoto photo2 = new HotelPhoto();
        photo2.setS3Key("https://example.com/photo2.jpg");
        photo2.setHotel(hotel);

        hotelPhotoRepository.saveAll(List.of(photo1, photo2));

        List<HotelPhoto> savedPhotos = hotelPhotoRepository.findAll();

        assertEquals(2, savedPhotos.size());
        for (HotelPhoto photo : savedPhotos) {
            assertNotNull(photo.getId());
            assertEquals(hotel.getId(), photo.getHotel().getId());
        }
    }

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

        Location location = new Location();
        location.setCountry("Country");
        location.setCity("City");
        location.setStreet("Main Street");
        location.setBuilding("123A");
        location.setZipCode("00000");
        location = locationRepository.save(location);

        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail(UUID.randomUUID() + "@mail.com");
        manager.setPasswordHash("hashedpassword123");
        manager.setRole(Role.RECEPTIONIST);
        manager.setPhone("123456789");
        manager = managerRepository.save(manager);

        hotel.setLocation(location);
        hotel.setManager(manager);

        return hotel;
    }
}
