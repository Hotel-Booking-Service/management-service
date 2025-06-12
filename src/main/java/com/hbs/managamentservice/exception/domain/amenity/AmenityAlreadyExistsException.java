package com.hbs.managamentservice.exception.domain.amenity;

import com.hbs.managamentservice.exception.base.ConflictException;

public class AmenityAlreadyExistsException extends ConflictException {

    private static final String ERROR_MESSAGE ="Amenity already exists";

    public AmenityAlreadyExistsException() {
        super(ERROR_MESSAGE);
    }
}
