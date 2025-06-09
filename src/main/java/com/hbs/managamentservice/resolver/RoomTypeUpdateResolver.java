package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.dto.request.UpdateRoomRequest;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.model.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomTypeUpdateResolver {

    private final RoomTypeResolver roomTypeResolver;

    public RoomType resolve(UpdateRoomRequest request, HotelRoom currentRoom) {
        if (request.getRoomTypeId() == null) {
            return currentRoom.getRoomType();
        }
        return roomTypeResolver.resolveById(request.getRoomTypeId());
    }
}
