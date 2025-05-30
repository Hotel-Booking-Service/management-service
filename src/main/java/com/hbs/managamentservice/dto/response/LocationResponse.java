package com.hbs.managamentservice.dto.response;

public record LocationResponse(
        String country,
        String city,
        String street,
        String building,
        String zipCode
) {
}
