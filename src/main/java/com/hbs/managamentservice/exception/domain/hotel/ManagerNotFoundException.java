package com.hbs.managamentservice.exception.domain.hotel;

import com.hbs.managamentservice.exception.base.NotFoundException;

public class ManagerNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "Manager not found";

    public ManagerNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
