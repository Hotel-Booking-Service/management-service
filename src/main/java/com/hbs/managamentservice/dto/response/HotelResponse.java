package com.hbs.managamentservice.dto.response;

import com.hbs.managamentservice.model.HotelStatus;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Ответ с данными отеля")
@Builder
public record HotelResponse(
        @Schema(description = "Уникальный идентификатор", example = "123")
        Long id,

        @Schema(description = "Название отеля", example = "Grand Plaza")
        String name,

        @Schema(description = "Описание отеля", example = "Современный отель…")
        String description,

        @Schema(description = "Класс отеля (звёзды)", example = "5")
        int stars,

        @Schema(description = "Статус отеля", example = "ACTIVE")
        HotelStatus status,

        @Schema(description = "Расположение отеля", implementation = LocationResponse.class)
        LocationResponse location,

        @ArraySchema(
                schema = @Schema(type = "string", format = "uri"),
                arraySchema = @Schema(description = "Список URL фотографий")
        )
        List<URI> photos,

        @Schema(description = "Статус удаления", example = "false")
        boolean isDeleted,

        @Schema(description = "Дата и время удаления", example = "2022-01-01T00:00:00Z")
        LocalDateTime deletedAt
) {
}
