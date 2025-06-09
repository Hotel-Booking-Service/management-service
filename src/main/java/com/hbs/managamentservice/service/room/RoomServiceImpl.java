package com.hbs.managamentservice.service.room;

import com.hbs.managamentservice.dto.request.CreateRoomRequest;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.dto.response.RoomResponse;
import com.hbs.managamentservice.exception.domain.hotel.HotelNotFoundException;
import com.hbs.managamentservice.exception.domain.room.RoomNotFoundException;
import com.hbs.managamentservice.mapper.HotelRoomMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.model.RoomType;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.repository.HotelRoomRepository;
import com.hbs.managamentservice.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final HotelRoomMapper hotelRoomMapper;
    private final HotelRoomRepository hotelRoomRepository;

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getRoomById(Long id) {
        HotelRoom room = hotelRoomRepository.findById(id)
                .orElseThrow(RoomNotFoundException::new);
        return hotelRoomMapper.toRoomResponse(room);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<RoomResponse> getRoomsByHotelId(Long hotelId, Pageable pageable) {
        hotelRepository.findById(hotelId)
                .orElseThrow(HotelNotFoundException::new);
        Page<HotelRoom> hotelRoomsPage = hotelRoomRepository.findAllByHotelId(hotelId, pageable);

        return PagedResponse.from(hotelRoomsPage, hotelRoomMapper::toRoomResponse);
    }

    @Override
    @Transactional
    public RoomResponse createRoom(CreateRoomRequest roomRequest) {
        Hotel hotel = getHotelById(roomRequest.getHotelId());
        RoomType roomType = getRoomTypeById(roomRequest.getRoomTypeId());
        HotelRoom room = hotelRoomMapper.toHotelRoom(roomRequest, hotel, roomType);
        room = hotelRoomRepository.save(room);
        return hotelRoomMapper.toRoomResponse(room);
    }

    private Hotel getHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(HotelNotFoundException::new);
    }

    private RoomType getRoomTypeById(Long roomTypeId) {
        return roomTypeRepository.findById(roomTypeId)
                .orElseThrow(RoomNotFoundException::new);
    }
}
