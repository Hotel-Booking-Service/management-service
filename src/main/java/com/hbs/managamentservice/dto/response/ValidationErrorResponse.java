package com.hbs.managamentservice.dto.response;

public record ValidationErrorResponse(
        String field,
        String message
) {
}
