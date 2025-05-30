package com.hbs.managamentservice.unit.exception.handler;

import com.hbs.managamentservice.dto.response.ErrorResponse;
import com.hbs.managamentservice.dto.response.ValidationErrorResponse;
import com.hbs.managamentservice.exception.handler.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
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
