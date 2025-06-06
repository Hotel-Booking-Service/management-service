package com.hbs.managamentservice.controller.room;

import com.hbs.managamentservice.dto.request.CreateRoomRequest;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.dto.response.RoomResponse;
import com.hbs.managamentservice.service.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HotelRoomControllerImpl implements HotelRoomController {

    private final RoomService roomService;

    @Override
    @GetMapping("/rooms/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponse getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @Override
    @GetMapping("/hotels/{id}/rooms")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<RoomResponse> getRoomsByHotelId(@PathVariable Long id, Pageable pageable) {
        return roomService.getRoomsByHotelId(id, pageable);
    }

    @Override
    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse createRoom(@RequestBody @Valid CreateRoomRequest roomRequest) {
        return roomService.createRoom(roomRequest);
    }
}
