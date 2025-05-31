package com.hbs.managamentservice.exception.domain.storage;

import com.hbs.managamentservice.exception.base.InvalidException;

public class EmptyFileException extends InvalidException {
    private static final String ERROR_MESSAGE = "File is empty";

    public EmptyFileException() {
        super(ERROR_MESSAGE);
    }
}
