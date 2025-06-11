package com.hbs.managamentservice.unit.resolver;

import com.hbs.managamentservice.dto.request.UpdateRoomRequest;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.model.RoomType;
import com.hbs.managamentservice.resolver.HotelRoomRelationResolver;
import com.hbs.managamentservice.resolver.RoomTypeResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelRoomRelationResolverTest {

    @Mock
    private RoomTypeResolver roomTypeResolver;

    @InjectMocks
    private HotelRoomRelationResolver relationResolver;

    private UpdateRoomRequest request;
    private HotelRoom hotelRoom;

    @BeforeEach
    void setUp() {
        request = mock(UpdateRoomRequest.class);
        hotelRoom = new HotelRoom();
    }

    @Test
    void resolveRelations_shouldSetRoomType_whenRoomTypeIdIsPresent() {
        Long roomTypeId = 42L;
        RoomType mockRoomType = new RoomType();

        when(request.getRoomTypeId()).thenReturn(roomTypeId);
        when(roomTypeResolver.resolveById(roomTypeId)).thenReturn(mockRoomType);

        relationResolver.resolveRelations(request, hotelRoom);

        verify(roomTypeResolver).resolveById(roomTypeId);
        assertEquals(mockRoomType, hotelRoom.getRoomType());
    }

    @Test
    void resolveRelations_shouldDoNothing_whenRoomTypeIdIsNull() {
        when(request.getRoomTypeId()).thenReturn(null);

        relationResolver.resolveRelations(request, hotelRoom);

        verifyNoInteractions(roomTypeResolver);
        assertNull(hotelRoom.getRoomType());
    }
}
