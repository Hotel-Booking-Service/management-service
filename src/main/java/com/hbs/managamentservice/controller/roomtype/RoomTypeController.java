package com.hbs.managamentservice.controller.roomtype;

import com.hbs.managamentservice.dto.request.RoomTypeRequest;
import com.hbs.managamentservice.dto.response.RoomTypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Tag(name = "RoomType", description = "API для управления типами комнат")
@RequestMapping("/api/v1/room-types")
public interface RoomTypeController {

    @Operation(
            summary = "Получить тип комнаты по идентификатору",
            description = "Получить тип комнаты по идентификатору"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Тип комнаты найден",
            content = @Content(schema = @Schema(implementation = RoomTypeResponse.class))
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    RoomTypeResponse getRoomTypeById(@PathVariable Long id);

    @Operation(
            summary = "Получить все типы комнат",
            description = "Получить все типы комнат"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Типы комнат найдены",
            content = @Content(schema = @Schema(implementation = RoomTypeResponse.class))
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<RoomTypeResponse> getAllRoomTypes();

    @Operation(
            summary = "Создать тип комнаты",
            description = "Создать тип комнаты"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Тип комнаты создан",
            content = @Content(schema = @Schema(implementation = RoomTypeResponse.class))
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RoomTypeResponse createRoomType(@RequestBody @Valid RoomTypeRequest roomTypeRequest);

    @Operation(
            summary = "Обновить тип комнаты",
            description = "Обновить тип комнаты"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Тип комнаты обновлен",
            content = @Content(schema = @Schema(implementation = RoomTypeResponse.class))
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    RoomTypeResponse updateRoomType(@PathVariable Long id, @RequestBody @Valid RoomTypeRequest roomTypeRequest);

    @Operation(
            summary = "Удалить тип комнаты",
            description = "Удалить тип комнаты"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Тип комнаты удален"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRoomType(@PathVariable Long id);
}
