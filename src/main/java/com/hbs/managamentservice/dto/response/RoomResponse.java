package com.hbs.managamentservice.dto.response;

import com.hbs.managamentservice.model.HotelRoomStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Ответ с данными комнаты")
public record RoomResponse(
        Long id,
        Long roomTypeId,
        Long hotelId,
        String number,
        int floor,
        HotelRoomStatus status,
        BigDecimal pricePerNight
) {
}
