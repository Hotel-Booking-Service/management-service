package com.hbs.managamentservice.integration.repository;

import com.hbs.managamentservice.model.BedType;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.model.HotelRoomStatus;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.model.Role;
import com.hbs.managamentservice.model.RoomCategory;
import com.hbs.managamentservice.model.RoomType;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.repository.HotelRoomRepository;
import com.hbs.managamentservice.repository.LocationRepository;
import com.hbs.managamentservice.repository.ManagerRepository;
import com.hbs.managamentservice.repository.RoomTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class HotelRoomRepositoryTest {

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void whenSaveHotelRoom_thenPersisted() {
        HotelRoom hotelRoom = createHotelRoom();
        HotelRoom saved = hotelRoomRepository.save(hotelRoom);

        assertNotNull(saved.getId());
        assertEquals("101", saved.getNumber());
        assertEquals(3, saved.getFloor());
        assertEquals(HotelRoomStatus.FREE, saved.getStatus());
        assertEquals(new BigDecimal("150.00"), saved.getPricePerNight());
    }

    @Test
    void whenFindById_thenReturnHotelRoom() {
        HotelRoom saved = hotelRoomRepository.save(createHotelRoom());
        Optional<HotelRoom> found = hotelRoomRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("101", found.get().getNumber());
    }

    @Test
    void whenUpdateHotelRoom_thenChangesPersisted() {
        HotelRoom hotelRoom = hotelRoomRepository.save(createHotelRoom());
        hotelRoom.setStatus(HotelRoomStatus.BUSY);
        hotelRoom.setPricePerNight(new BigDecimal("200.00"));

        HotelRoom updated = hotelRoomRepository.save(hotelRoom);

        assertEquals(HotelRoomStatus.BUSY, updated.getStatus());
        assertEquals(new BigDecimal("200.00"), updated.getPricePerNight());
    }

    @Test
    void whenDeleteHotelRoom_thenNotFound() {
        HotelRoom hotelRoom = hotelRoomRepository.save(createHotelRoom());
        hotelRoomRepository.delete(hotelRoom);

        Optional<HotelRoom> found = hotelRoomRepository.findById(hotelRoom.getId());
        assertFalse(found.isPresent());
    }

    private HotelRoom createHotelRoom() {
        RoomType roomType = roomTypeRepository.save(
                new RoomType(null, RoomCategory.DELUXE, "Deluxe Room", BedType.KING_SIZE, 35.0, 2)
        );

        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail("john.doe@example.com");
        manager.setPasswordHash("hashed_password");
        manager.setPhone("+123456789");
        manager.setRole(Role.RECEPTIONIST);
        manager.setHireDate(LocalDateTime.now());
        manager.setActive(true);
        manager = managerRepository.save(manager);

        Location location = new Location();
        location.setCountry("USA");
        location.setCity("New York");
        location.setStreet("5th Avenue");
        location.setBuilding("101A");
        location.setZipCode("10001");
        location = locationRepository.save(location);

        Hotel hotel = new Hotel();
        hotel.setName("Hotel 1");
        hotel.setDescription("Hotel Description");
        hotel.setManager(manager);
        hotel.setLocation(location);
        hotel = hotelRepository.save(hotel);

        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setRoomType(roomType);
        hotelRoom.setHotel(hotel);
        hotelRoom.setNumber("101");
        hotelRoom.setFloor(3);
        hotelRoom.setStatus(HotelRoomStatus.FREE);
        hotelRoom.setPricePerNight(new BigDecimal("150.00"));

        return hotelRoom;
    }
}
