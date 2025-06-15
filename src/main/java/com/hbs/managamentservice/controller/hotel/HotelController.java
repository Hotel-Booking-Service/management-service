package com.hbs.managamentservice.controller.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Hotels", description = "API для управления отелями")
@RequestMapping("/api/v1/hotels")
public interface HotelController {

    @Operation(
            summary = "Получить информацию о всех отелях",
            description = "Возвращает информацию о всех отелях.",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Номер страницы (начиная с 0)",
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "Количество элементов на странице",
                            example = "10"
                    ),
                    @Parameter(
                            name = "sort",
                            description = "Сортировка: имя поля и направление (`asc` или `desc`)",
                            example = "stars,desc"
                    )
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Информация о всех отелях успешно получена",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HotelResponse.class)
            )
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    PagedResponse<HotelResponse> getAllHotels(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            @Parameter(hidden = true) Pageable pageable);

    @Operation(
            summary = "Получить информацию о отеле по идентификатору",
            description = "Возвращает информацию о отеле по переданному идентификатору."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Информация о отеле успешно получена",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HotelResponse.class)
            )
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    HotelResponse getHotelById(@Parameter(description = "Идентификатор отеля") @PathVariable Long id);

    @Operation(
            summary = "Создать новый отель",
            description = "Создаёт отель по переданным данным и возвращает информацию о нём."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Отель успешно создан",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HotelResponse.class)
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    HotelResponse createHotel(@RequestBody @Valid CreateHotelRequest hotel);

    @Operation(
            summary = "Частичное обновление отеля",
            description = "Обновляет только указные поля у отеля."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Отель успешно обновлен",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HotelResponse.class)
            )
    )
    @PatchMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    HotelResponse updateHotel(@PathVariable Long hotelId, @Valid @RequestBody UpdateHotelRequest request);

    @Operation(
            summary = "Удалить отель",
            description = "Удаляет отель по переданному идентификатору."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Отель успешно удалeн"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteHotel(@Parameter(description = "Идентификатор отеля") @PathVariable Long id);
}
