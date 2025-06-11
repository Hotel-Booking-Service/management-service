package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.dto.request.UpdateRoomRequest;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.model.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelRoomRelationResolver {

    private final RoomTypeResolver roomTypeResolver;

    public void resolveRelations(UpdateRoomRequest request, HotelRoom hotelRoom) {
        if (request.getRoomTypeId() != null) {
            RoomType roomType = roomTypeResolver.resolveById(request.getRoomTypeId());

            hotelRoom.setRoomType(roomType);
        }
    }
}
