package com.hbs.managamentservice.exception.domain.hotel;

import com.hbs.managamentservice.exception.base.NotFoundException;

public class LocationNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Location not found";

    public LocationNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
