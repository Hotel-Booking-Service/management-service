package com.hbs.managamentservice.controller.roomtype;

import com.hbs.managamentservice.dto.request.RoomTypeRequest;
import com.hbs.managamentservice.dto.response.RoomTypeResponse;
import com.hbs.managamentservice.service.roomtype.RoomTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room-types")
@RequiredArgsConstructor
public class RoomTypeControllerImpl implements RoomTypeController {

    private final RoomTypeService roomTypeService;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoomTypeResponse getRoomTypeById(@PathVariable Long id) {
        return roomTypeService.getRoomTypeById(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoomTypeResponse> getAllRoomTypes() {
        return roomTypeService.getAllRoomTypes();
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomTypeResponse createRoomType(@RequestBody @Valid RoomTypeRequest roomTypeRequest) {
        return roomTypeService.createRoomType(roomTypeRequest);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoomTypeResponse updateRoomType(@PathVariable Long id, @RequestBody @Valid RoomTypeRequest roomTypeRequest) {
        return roomTypeService.updateRoomType(id, roomTypeRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoomType(@PathVariable Long id) {
        roomTypeService.deleteRoomType(id);
    }
}
