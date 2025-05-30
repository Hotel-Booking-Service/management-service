package com.hbs.managamentservice.dto.request;

import com.hbs.managamentservice.model.HotelStatus;
import com.hbs.managamentservice.validation.annotation.HotelStatusSubset;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание нового отеля")
public class CreateHotelRequest {

    @Schema(
            description = "Название отеля",
            example = "Grand Plaza",
            minLength = 3, maxLength = 100
    )
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100)
    private String name;

    @Schema(
            description = "Описание отеля",
            example = "Современный отель в центре города с высоким уровнем сервиса.",
            minLength = 10, maxLength = 1500
    )
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1500)
    private String description;

    @Schema(
            description = "Количество звёзд (от 1 до 5)",
            example = "5",
            minimum = "1", maximum = "5"
    )
    @NotNull(message = "Stars is required")
    @Min(1)
    @Max(5)
    private Integer stars;

    @Schema(
            description = "Статус отеля",
            example = "ACTIVE",
            allowableValues = {"PLANNED", "ACTIVE"}
    )
    @NotNull
    @HotelStatusSubset(anyOf = {HotelStatus.PLANNED, HotelStatus.ACTIVE})
    private HotelStatus status;

    @Valid
    @Schema(description = "Адрес отеля")
    @NotNull(message = "Location is required")
    private LocationRequest location;
}
