package com.hbs.managamentservice.exception.domain.roomtype;

import com.hbs.managamentservice.exception.base.NotFoundException;

public class RoomTypeNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Room type not found";

    public RoomTypeNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
