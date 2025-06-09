package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.exception.domain.room.RoomNotFoundException;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.repository.HotelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HotelRoomResolver implements EntityResolver<HotelRoom> {

    private final HotelRoomRepository hotelRoomRepository;

    @Override
    @Transactional(readOnly = true)
    public HotelRoom resolveById(Long id) {
        return hotelRoomRepository.findById(id)
                .orElseThrow(RoomNotFoundException::new);
    }
}
