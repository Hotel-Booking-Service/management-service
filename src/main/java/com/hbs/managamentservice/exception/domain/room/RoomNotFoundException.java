package com.hbs.managamentservice.exception.domain.room;

import com.hbs.managamentservice.exception.base.NotFoundException;

public class RoomNotFoundException extends NotFoundException {

    private static final String ERROR_MESSAGE = "Room not found";

    public RoomNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
