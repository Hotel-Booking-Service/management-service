package com.hbs.managamentservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с локацией отеля")
public record LocationResponse(
        String country,
        String city,
        String street,
        String building,
        String zipCode
) {
}
