package com.hbs.managamentservice.service.roomtype;

import com.hbs.managamentservice.dto.request.RoomTypeRequest;
import com.hbs.managamentservice.dto.response.RoomTypeResponse;

import java.util.List;

public interface RoomTypeService {

    RoomTypeResponse getRoomTypeById(Long id);

    List<RoomTypeResponse> getAllRoomTypes();

    RoomTypeResponse createRoomType(RoomTypeRequest roomTypeDto);

    RoomTypeResponse updateRoomType(Long id, RoomTypeRequest roomTypeDto);

    void deleteRoomType(Long id);
}
