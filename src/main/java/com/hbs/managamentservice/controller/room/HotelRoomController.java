package com.hbs.managamentservice.controller.room;

import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.dto.response.RoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Rooms", description = "API для управления комнатами")
@RequestMapping("/api/v1")
public interface HotelRoomController {

    @Operation(
            summary = "Получить комнату по идентификатору",
            description = "Получить комнату по идентификатору"
    )
    @ApiResponse(responseCode = "200", description = "Комната найдена")
    @GetMapping("/rooms/{id}")
    @ResponseStatus(HttpStatus.OK)
    RoomResponse getRoomById(@PathVariable Long id);

    @Operation(
            summary = "Получить комнаты отеля по идентификатору",
            description = "Получить комнаты отеля по идентификатору"
    )
    @ApiResponse(responseCode = "200", description = "Комнаты найдены")
    @GetMapping("/hotels/{id}/rooms")
    @ResponseStatus(HttpStatus.OK)
    PagedResponse<RoomResponse> getRoomsByHotelId(@PathVariable Long id, @Parameter(hidden = true) Pageable pageable);
}
