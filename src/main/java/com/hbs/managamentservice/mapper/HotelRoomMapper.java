package com.hbs.managamentservice.mapper;

import com.hbs.managamentservice.dto.response.RoomResponse;
import com.hbs.managamentservice.model.HotelRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface HotelRoomMapper {

    @Mapping(target = "roomTypeId", source = "room.roomType.id")
    @Mapping(target = "hotelId", source = "room.hotel.id")
    RoomResponse toRoomResponse(HotelRoom room);
}
