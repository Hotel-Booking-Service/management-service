package com.hbs.managamentservice.exception.domain.hotel;

import com.hbs.managamentservice.exception.base.NotFoundException;

public class HotelNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Hotel not found";

    public HotelNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
