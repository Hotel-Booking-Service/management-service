package com.hbs.managamentservice.exception.domain.storage;

import com.hbs.managamentservice.exception.base.NotFoundException;

public class FileNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "File not found";

    public FileNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public FileNotFoundException(String s3Key) {
        super(ERROR_MESSAGE + "s3Key: " + s3Key);
    }
}
