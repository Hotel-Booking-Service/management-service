package com.hbs.managamentservice.exception.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public abstract class InvalidException extends RuntimeException {
    protected InvalidException(String message) {
        super(message);
    }
}

