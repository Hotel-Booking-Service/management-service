package com.hbs.managamentservice.controller.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Hotels", description = "API для управления отелями")
@RequestMapping("/api/v1/hotels")
public interface HotelController {

    @Operation(
            summary = "Создать новый отель",
            description = "Создаёт отель по переданным данным и возвращает информацию о нём."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Отель успешно создан",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HotelResponse.class)
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    HotelResponse createHotel(@RequestBody @Valid CreateHotelRequest hotel);

    @Operation(
            summary = "Получить фото по id",
            description = "Получает фото по id и перенаправляет по временному url."
    )
    @ApiResponse(
            responseCode = "302",
            description = "Успешное получение и перенаправление по ссылке"
    )
    @GetMapping("/photos/{photoId}")
    ResponseEntity<Void> getPhotoById(@PathVariable("photoId") Long photoId);
}
