package com.hbs.managamentservice.dto.request;

import com.hbs.managamentservice.model.BedType;
import com.hbs.managamentservice.model.RoomCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание/изменение типа комнаты")
public class RoomTypeRequest {

    @Schema(description = "Название типа комнаты", example = "STANDARD")
    @NotNull(message = "Name is required")
    private RoomCategory name;

    @Schema(description = "Описание типа комнаты", example = "Тип комнаты для людей с ограниченными возможностями")
    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 1500, message = "Description must be between {min} and {max} characters")
    private String description;

    @Schema(description = "Тип кровати", example = "SINGLE")
    @NotNull(message = "Bed type is required")
    private BedType bedType;

    @Schema(description = "Площадь комнаты", example = "20.5")
    @NotNull(message = "Area is required")
    @Positive(message = "Area must be positive")
    private double area;

    @Schema(description = "Максимальное количество гостей", example = "2")
    @NotNull(message = "Max guests is required")
    @Positive(message = "Max guests must be positive")
    private int maxGuests;
}
