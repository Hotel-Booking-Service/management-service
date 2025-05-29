package com.hbs.managamentservice.mapper;

import com.hbs.managamentservice.dto.response.ErrorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ExceptionMapper {

    @Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", expression =  "java(getStatus(exception).value())")
    @Mapping(target = "error", expression =  "java(getStatus(exception).getReasonPhrase())")
    @Mapping(target = "message", source = "exception.message")
    @Mapping(target = "path", source = "path")
    ErrorResponse toErrorResponse(Exception exception, String path);

    default HttpStatus getStatus(Exception exception) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
        return responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
    }
    
}
