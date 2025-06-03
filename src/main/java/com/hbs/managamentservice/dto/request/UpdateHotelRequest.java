package com.hbs.managamentservice.dto.request;

import com.hbs.managamentservice.model.HotelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Patch запрос на обновление отеля")
public class UpdateHotelRequest {

    @Schema(
            description = "Название отеля",
            example = "Grand Plaza",
            minLength = 3, maxLength = 100
    )
    @Size(min = 3, max = 100)
    private String name;

    @Schema(
            description = "Описание отеля",
            example = "Современный отель в центре города с высоким уровнем сервиса.",
            minLength = 10, maxLength = 2500
    )
    @Size(min = 10, max = 2500)
    private String description;

    @Schema(description = "Адрес отеля")
    private Long locationId;

    @Schema(
            description = "Звездность отеля",
            example = "5"
    )
    @Size(min = 1, max = 5)
    private Integer stars;

    @Schema(
            description = "Статус отеля",
            example = "ACTIVE",
            allowableValues = {"PLANNED", "ACTIVE"}
    )
    private HotelStatus status;

    @Schema(description = "Менеджер отеля")
    private Long managerId;

    @Schema(description = "Удобства отеля")
    private Set<Long> amenityIds;

    @Schema(description = "Фотки отеля")
    private Set<Long> photoIds;

    @Schema(
            description = "Статус удаления",
            example = "false"
    )
    private Boolean isDeleted;

}
