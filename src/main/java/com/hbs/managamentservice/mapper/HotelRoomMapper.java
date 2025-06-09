package com.hbs.managamentservice.mapper;

import com.hbs.managamentservice.dto.request.CreateRoomRequest;
import com.hbs.managamentservice.dto.request.UpdateRoomRequest;
import com.hbs.managamentservice.dto.response.RoomResponse;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.model.RoomType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING)
public interface HotelRoomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "roomRequest.status")
    @Mapping(target = "hotel", source = "hotel")
    @Mapping(target = "roomType", source = "roomType")
    HotelRoom toHotelRoom(CreateRoomRequest roomRequest, Hotel hotel, RoomType roomType);

    @Mapping(target = "roomTypeId", source = "room.roomType.id")
    @Mapping(target = "hotelId", source = "room.hotel.id")
    RoomResponse toRoomResponse(HotelRoom room);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "status", source = "roomRequest.status")
    @Mapping(target = "roomType", expression = "java(roomType != null ? roomType : hotelRoom.getRoomType())")
    void updateHotelRoom(UpdateRoomRequest roomRequest, @MappingTarget HotelRoom hotelRoom, @Context RoomType roomType);
}
