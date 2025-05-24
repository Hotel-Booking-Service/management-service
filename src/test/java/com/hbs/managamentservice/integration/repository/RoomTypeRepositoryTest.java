package com.hbs.managamentservice.integration.repository;

import com.hbs.managamentservice.model.BedType;
import com.hbs.managamentservice.model.RoomCategory;
import com.hbs.managamentservice.model.RoomType;
import com.hbs.managamentservice.repository.RoomTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class RoomTypeRepositoryTest {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Test
    void whenSaveRoomType_thenPersisted() {
        RoomType roomType = createRoomType(RoomCategory.DELUXE, BedType.KING_SIZE);
        RoomType saved = roomTypeRepository.save(roomType);

        assertNotNull(saved.getId());
        assertEquals(RoomCategory.DELUXE, saved.getName());
        assertEquals(BedType.KING_SIZE, saved.getBedType());
        assertEquals(2, saved.getMaxGuests());
    }

    @Test
    void whenFindById_thenReturnRoomType() {
        RoomType roomType = roomTypeRepository.save(createRoomType(RoomCategory.SUITE, BedType.DOUBLE));
        Optional<RoomType> found = roomTypeRepository.findById(roomType.getId());

        assertTrue(found.isPresent());
        assertEquals(RoomCategory.SUITE, found.get().getName());
    }

    @Test
    void whenUpdateRoomType_thenChangesPersisted() {
        RoomType roomType = roomTypeRepository.save(createRoomType(RoomCategory.STANDARD, BedType.SINGLE));
        roomType.setDescription("Updated description");
        roomType.setMaxGuests(3);
        RoomType updated = roomTypeRepository.save(roomType);

        assertEquals("Updated description", updated.getDescription());
        assertEquals(3, updated.getMaxGuests());
    }

    @Test
    void whenDeleteRoomType_thenNotFound() {
        RoomType roomType = roomTypeRepository.save(createRoomType(RoomCategory.PRESIDENTIAL, BedType.KING_SIZE));
        roomTypeRepository.delete(roomType);

        Optional<RoomType> found = roomTypeRepository.findById(roomType.getId());
        assertFalse(found.isPresent());
    }

    private RoomType createRoomType(RoomCategory category, BedType bedType) {
        RoomType roomType = new RoomType();
        roomType.setName(category);
        roomType.setDescription("Spacious and elegant room.");
        roomType.setBedType(bedType);
        roomType.setArea(35.5);
        roomType.setMaxGuests(2);
        return roomType;
    }
}
