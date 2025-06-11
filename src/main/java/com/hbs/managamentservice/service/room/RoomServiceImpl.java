package com.hbs.managamentservice.service.room;

import com.hbs.managamentservice.dto.request.CreateRoomRequest;
import com.hbs.managamentservice.dto.request.UpdateRoomRequest;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.dto.response.RoomResponse;
import com.hbs.managamentservice.mapper.HotelRoomMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.model.RoomType;
import com.hbs.managamentservice.repository.HotelRoomRepository;
import com.hbs.managamentservice.resolver.HotelResolver;
import com.hbs.managamentservice.resolver.HotelRoomRelationResolver;
import com.hbs.managamentservice.resolver.HotelRoomResolver;
import com.hbs.managamentservice.resolver.RoomTypeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final HotelRoomMapper hotelRoomMapper;
    private final HotelRoomRepository hotelRoomRepository;
    private final HotelRoomResolver hotelRoomResolver;
    private final RoomTypeResolver roomTypeResolver;
    private final HotelResolver hotelResolver;
    private final HotelRoomRelationResolver hotelRoomRelationResolver;

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getRoomById(Long id) {
        HotelRoom room = hotelRoomResolver.resolveById(id);
        return hotelRoomMapper.toRoomResponse(room);
    }


    @Override
    @Transactional(readOnly = true)
    public PagedResponse<RoomResponse> getRoomsByHotelId(Long hotelId, Pageable pageable) {
        hotelResolver.resolveById(hotelId);
        Page<HotelRoom> hotelRoomsPage = hotelRoomRepository.findAllByHotelId(hotelId, pageable);

        return PagedResponse.from(hotelRoomsPage, hotelRoomMapper::toRoomResponse);
    }

    @Override
    @Transactional
    public RoomResponse createRoom(CreateRoomRequest roomRequest) {
        Hotel hotel = hotelResolver.resolveById(roomRequest.getHotelId());
        RoomType roomType = roomTypeResolver.resolveById(roomRequest.getRoomTypeId());
        HotelRoom room = hotelRoomMapper.toHotelRoom(roomRequest, hotel, roomType);
        room = hotelRoomRepository.save(room);
        return hotelRoomMapper.toRoomResponse(room);
    }


    @Override
    @Transactional
    public RoomResponse updateRoom(Long id, UpdateRoomRequest roomRequest) {
        HotelRoom hotelRoom = hotelRoomResolver.resolveById(id);

        hotelRoomRelationResolver.resolveRelations(roomRequest, hotelRoom);

        hotelRoomMapper.updateHotelRoom(roomRequest, hotelRoom);

        return hotelRoomMapper.toRoomResponse(hotelRoom);
    }
}
