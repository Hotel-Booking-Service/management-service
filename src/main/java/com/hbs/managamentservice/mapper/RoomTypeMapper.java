package com.hbs.managamentservice.mapper;

import com.hbs.managamentservice.dto.request.RoomTypeRequest;
import com.hbs.managamentservice.dto.response.RoomTypeResponse;
import com.hbs.managamentservice.model.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RoomTypeMapper {

    @Mapping(target = "id", ignore = true)
    RoomType toRoomType(RoomTypeRequest roomTypeRequest);

    RoomTypeResponse toRoomTypeResponse(RoomType roomType);

    @Mapping(target = "id", ignore = true)
    void updateRoomType(RoomTypeRequest roomTypeRequest, @MappingTarget RoomType roomType);
}
