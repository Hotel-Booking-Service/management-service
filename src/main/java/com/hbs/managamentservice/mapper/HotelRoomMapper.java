package com.hbs.managamentservice.mapper;

import com.hbs.managamentservice.dto.request.CreateRoomRequest;
import com.hbs.managamentservice.dto.response.RoomResponse;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.model.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface HotelRoomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", source = "roomRequest.status")
    @Mapping(target = "hotel", source = "hotel")
    @Mapping(target = "roomType", source = "roomType")
    HotelRoom toHotelRoom(CreateRoomRequest roomRequest, Hotel hotel, RoomType roomType);

    @Mapping(target = "roomTypeId", source = "room.roomType.id")
    @Mapping(target = "hotelId", source = "room.hotel.id")
    RoomResponse toRoomResponse(HotelRoom room);
}
