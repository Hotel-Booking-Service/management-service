package com.hbs.managamentservice.controller.amenity;

import com.hbs.managamentservice.dto.request.CreateAmenityRequest;
import com.hbs.managamentservice.dto.request.UpdateAmenityRequest;
import com.hbs.managamentservice.dto.response.AmenityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Tag(name = "Amenities", description = "API для управления с удобствами")
@RequestMapping("/api/v1/amenities")
public interface AmenityController {

    @Operation(
            summary = "Получить все удобства",
            description = "Возвращает список всех удобств"
    )
    @ApiResponse(responseCode = "200", description = "Список удобств")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<AmenityResponse> getAmenities();

    @Operation(
            summary = "Получить удобство по идентификатору",
            description = "Возвращает удобство по идентификатору"
    )
    @ApiResponse(responseCode = "200", description = "Удобство")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AmenityResponse getAmenity(@PathVariable Long id);

    @Operation(
            summary = "Создать удобство",
            description = "Создает новое удобство"
    )
    @ApiResponse(responseCode = "201", description = "Удобство создано")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AmenityResponse createAmenity(@ModelAttribute @Valid CreateAmenityRequest request);

    @Operation(
            summary = "Обновить удобство",
            description = "Обновляет удобство по идентификатору"
    )
    @ApiResponse(responseCode = "200", description = "Удобство обновлено")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    AmenityResponse updateAmenity(@PathVariable Long id, @ModelAttribute @Valid UpdateAmenityRequest request);

    @Operation(
            summary = "Удалить удобство",
            description = "Удаляет удобство по идентификатору"
    )
    @ApiResponse(responseCode = "204", description = "Удобство удалено")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAmenity(@PathVariable Long id);
}