package com.hbs.managamentservice.unit.exception.handler;

import com.hbs.managamentservice.dto.response.ErrorResponse;
import com.hbs.managamentservice.dto.response.ValidationErrorResponse;
import com.hbs.managamentservice.exception.base.ConflictException;
import com.hbs.managamentservice.exception.base.InvalidException;
import com.hbs.managamentservice.exception.base.NotFoundException;
import com.hbs.managamentservice.exception.domain.amenity.AmenityAlreadyExistsException;
import com.hbs.managamentservice.exception.domain.storage.EmptyFileException;
import com.hbs.managamentservice.exception.domain.storage.FileNotFoundException;
import com.hbs.managamentservice.exception.handler.GlobalExceptionHandler;
import com.hbs.managamentservice.mapper.ExceptionMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private ExceptionMapper exceptionMapper;

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    void testHandleInvalidException_shouldReturnErrorResponse() {
        InvalidException invalidException = new EmptyFileException();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), 400, invalidException.getMessage(),
                "Error", "/", List.of());

        when(httpServletRequest.getRequestURI()).thenReturn("/");
        when(exceptionMapper.toErrorResponse(invalidException, "/")).thenReturn(errorResponse);

        ErrorResponse actualErrorResponse = exceptionHandler.handleInvalidException(invalidException, httpServletRequest);

        assertEquals(400, actualErrorResponse.status());
        assertEquals("File is empty", actualErrorResponse.error());
        assertEquals(errorResponse.message(), actualErrorResponse.message());
        assertEquals("/", actualErrorResponse.path());
        assertTrue(actualErrorResponse.errors().isEmpty());

        verify(exceptionMapper).toErrorResponse(invalidException, "/");
    }

    @Test
    void testHandleNotFoundException_shouldReturnErrorResponse() {
        NotFoundException notFoundException = new FileNotFoundException();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), 404, notFoundException.getMessage(),
                "Error", "/", List.of());

        when(httpServletRequest.getRequestURI()).thenReturn("/");
        when(exceptionMapper.toErrorResponse(notFoundException, "/")).thenReturn(errorResponse);

        ErrorResponse actualErrorResponse = exceptionHandler.handleNotFoundException(notFoundException, httpServletRequest);

        assertEquals(404, actualErrorResponse.status());
        assertEquals("File not found", actualErrorResponse.error());
        assertEquals(errorResponse.message(), actualErrorResponse.message());
        assertEquals("/", actualErrorResponse.path());
        assertTrue(actualErrorResponse.errors().isEmpty());
        verify(exceptionMapper).toErrorResponse(notFoundException, "/");
    }

    @Test
    void handleConflictException_shouldReturnErrorResponse() {
        ConflictException ex = new AmenityAlreadyExistsException();
        HttpServletRequest request = mock(HttpServletRequest.class);
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), 409, ex.getMessage(),
                "Conflict", "/api/test", List.of());

        when(request.getRequestURI()).thenReturn("/api/test");
        when(exceptionMapper.toErrorResponse(ex, "/api/test")).thenReturn(errorResponse);

        ErrorResponse response = exceptionHandler.handlerConflictException(ex, request);

        assertEquals(HttpStatus.CONFLICT.value(), response.status());
        assertEquals("Amenity already exists", response.error());
        assertEquals("Conflict", response.message());
        assertEquals("/api/test", response.path());
        assertTrue(response.errors().isEmpty());
    }

    @Test
    void handleMethodArgumentNotValidException_shouldReturnErrorResponse() {
        FieldError fieldError1 = new FieldError("objectName", "field1", "must not be blank");
        FieldError fieldError2 = new FieldError("objectName", "field2", "must be a valid email");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/test");

        ErrorResponse response = exceptionHandler.handleMethodArgumentNotValidException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.status());
        assertEquals("Error", response.error());
        assertEquals("Bad Request", response.message());
        assertEquals("/api/test", response.path());

        List<ValidationErrorResponse> errors = response.errors();
        assertEquals(2, errors.size());

        assertTrue(errors.stream().anyMatch(e -> e.field().equals("field1") && e.message().equals("must not be blank")));
        assertTrue(errors.stream().anyMatch(e -> e.field().equals("field2") && e.message().equals("must be a valid email")));
    }
}
