package com.hbs.managamentservice.exception.handler;

import com.hbs.managamentservice.dto.response.ErrorResponse;
import com.hbs.managamentservice.exception.base.InvalidException;
import com.hbs.managamentservice.exception.base.NotFoundException;
import com.hbs.managamentservice.mapper.ExceptionMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalErrorHandler {

    private final ExceptionMapper exceptionMapper;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidException.class)
    public ErrorResponse handleInvalidException(InvalidException ex, HttpServletRequest request) {
        return exceptionMapper.toErrorResponse(ex, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        return exceptionMapper.toErrorResponse(ex, request.getRequestURI());
    }
}