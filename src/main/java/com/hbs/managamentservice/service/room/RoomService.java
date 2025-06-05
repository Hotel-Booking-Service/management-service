package com.hbs.managamentservice.service.room;

import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.dto.response.RoomResponse;
import org.springframework.data.domain.Pageable;

public interface RoomService {

    RoomResponse getRoomById(Long id);

    PagedResponse<RoomResponse> getRoomsByHotelId(Long hotelId, Pageable pageable);
}
