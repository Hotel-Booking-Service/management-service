package com.hbs.managamentservice.controller.photo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Hotels", description = "API для управления отелями")
@RequestMapping("/api/v1/hotels")
public interface HotelPhotoController {

    @Operation(
            summary = "Получить фото по id",
            description = "Получает фото по id и перенаправляет по временному url."
    )
    @ApiResponse(
            responseCode = "302",
            description = "Успешное получение и перенаправление по ссылке"
    )
    @GetMapping("/photos/{photoId}")
    ResponseEntity<Void> getPhotoById(@PathVariable Long photoId);
}
