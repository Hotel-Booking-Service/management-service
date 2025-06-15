package com.hbs.managamentservice.controller.photo;

import com.hbs.managamentservice.dto.response.PhotoUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Photos", description = "API для фотографиями отеля")
@RequestMapping("/api/v1")
public interface HotelPhotoController {

    @Operation(
            summary = "Получить фото по id",
            description = "Получает фото по id и перенаправляет по временному url."
    )
    @ApiResponse(
            responseCode = "302",
            description = "Успешное получение и перенаправление по ссылке"
    )
    @GetMapping("/hotels/photos/{photoId}")
    @ResponseStatus(HttpStatus.FOUND)
    ResponseEntity<Void> getPhotoById(@PathVariable Long photoId);

    @Operation(
            summary = "Получить фото для отеля",
            description = "Получает фото для отеля."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешное получение фото"
    )
    @GetMapping("/hotels/{id}/photos")
    @ResponseStatus(HttpStatus.OK)
    List<PhotoUploadResponse> getHotelPhotos(@PathVariable Long id);

    @Operation(
            summary = "Создать фото для отеля",
            description = "Создает фото для отеля."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Фото успешно создано"
    )
    @PostMapping(value = "/hotels/{id}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    List<PhotoUploadResponse> createHotelPhotos(@PathVariable Long id, @RequestParam List<MultipartFile> photos);

    @Operation(
            summary = "Удалить фото по id",
            description = "Удаляет фото по id."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Фото успешно удалено"
    )
    @DeleteMapping("/hotels/photos/{photoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePhotoById(@PathVariable Long photoId);
}
