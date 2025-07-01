package com.hbs.managamentservice.exception.domain.photo;

import com.hbs.managamentservice.exception.base.NotFoundException;

public class PhotoNotFoundException extends NotFoundException {

    private static final String ERROR_MESSAGE = "Photo not found";

    public PhotoNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
