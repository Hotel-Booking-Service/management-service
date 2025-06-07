package com.hbs.managamentservice.dto.response;

import com.hbs.managamentservice.model.BedType;
import com.hbs.managamentservice.model.RoomCategory;

public record RoomTypeResponse(
        Long id,
        RoomCategory name,
        String description,
        BedType bedType,
        double area,
        int maxGuests) {
}
