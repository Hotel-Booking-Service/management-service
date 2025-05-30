package com.hbs.managamentservice.exception.handler;

import com.hbs.managamentservice.dto.response.ErrorResponse;
import com.hbs.managamentservice.dto.response.ValidationErrorResponse;
import com.hbs.managamentservice.exception.base.InvalidException;
import com.hbs.managamentservice.exception.base.NotFoundException;
import com.hbs.managamentservice.mapper.ExceptionMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

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

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ValidationErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationErrorResponse(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error",
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getRequestURI(),
                errors
        );
    }
}