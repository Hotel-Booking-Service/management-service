package com.hbs.managamentservice.exception.domain.hotel;

import com.hbs.managamentservice.exception.base.NotFoundException;

public class AmenityNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Amenity not found";

    public AmenityNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
