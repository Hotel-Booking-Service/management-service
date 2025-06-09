package com.hbs.managamentservice.dto.request;

import com.hbs.managamentservice.model.HotelRoomStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на обновление комнаты")
public class UpdateRoomRequest {

    @Schema(description = "Номер комнаты", example = "101")
    @Size(min = 1, max = 20, message = "Room number must be between {min} and {max} characters")
    private String number;

    @Schema(description = "Статус комнаты", example = "FREE")
    private HotelRoomStatus status;

    @Schema(description = "ID типа комнаты", example = "1")
    private Long roomTypeId;

    @Schema(description = "Этаж комнаты", example = "5")
    @Min(value = 1, message = "Floor must be greater than or equal to {value}")
    @Max(value = 50, message = "Floor must be less than or equal to {value}")
    private Integer floor;

    @Schema(description = "Цена за ночь", example = "100.00")
    @Positive(message = "Price per night must be positive")
    private BigDecimal pricePerNight;
}
