package com.hbs.managamentservice.dto.request;

import com.hbs.managamentservice.model.HotelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Size(min = 3, max = 100, message = "Min length = 3 ; Max length = 100")
    private String name;

    @Schema(
            description = "Описание отеля",
            example = "Современный отель в центре города с высоким уровнем сервиса.",
            minLength = 10, maxLength = 2500
    )
    @Size(min = 10, max = 2500, message = "Min length = 10 ; Max length = 2500")
    private String description;

    @Schema(description = "Адрес отеля")
    private Long locationId;

    @Schema(
            description = "Звездность отеля",
            example = "5"
    )
    @Min(value = 1, message = "Min stars is 1")
    @Max(value = 5, message = "Min stars is 5")
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

    @Schema(
            description = "Статус удаления",
            example = "false"
    )
    private Boolean isDeleted;

}
