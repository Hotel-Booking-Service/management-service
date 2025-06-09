package com.hbs.managamentservice.unit.service.room;

import com.hbs.managamentservice.dto.request.CreateRoomRequest;
import com.hbs.managamentservice.dto.request.UpdateRoomRequest;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.dto.response.RoomResponse;
import com.hbs.managamentservice.mapper.HotelRoomMapper;
import com.hbs.managamentservice.model.BedType;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelRoom;
import com.hbs.managamentservice.model.HotelRoomStatus;
import com.hbs.managamentservice.model.HotelStatus;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.RoomCategory;
import com.hbs.managamentservice.model.RoomType;
import com.hbs.managamentservice.repository.HotelRoomRepository;
import com.hbs.managamentservice.resolver.HotelResolver;
import com.hbs.managamentservice.resolver.HotelRoomResolver;
import com.hbs.managamentservice.resolver.RoomTypeResolver;
import com.hbs.managamentservice.resolver.RoomTypeUpdateResolver;
import com.hbs.managamentservice.service.room.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomTypeResolver roomTypeResolver;

    @Mock
    private HotelResolver hotelResolver;

    @Mock
    private HotelRoomResolver hotelRoomResolver;

    @Mock
    private RoomTypeUpdateResolver roomTypeUpdateResolver;

    @Mock
    private HotelRoomRepository hotelRoomRepository;

    @Mock
    private HotelRoomMapper hotelRoomMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    void getAllHotelRooms_shouldReturnRoomResponseList() {
        Hotel hotel = getHotel();
        HotelRoom hotelRoom = getHotelRoom();

        RoomResponse roomResponse = new RoomResponse(1L, 1L, 1L, "Test Room Number", 5, HotelRoomStatus.FREE, BigDecimal.valueOf(150));

        Pageable pageable = PageRequest.of(0, 10);

        when(hotelResolver.resolveById(1L)).thenReturn(hotel);
        when(hotelRoomRepository.findAllByHotelId(1L, pageable)).thenReturn(new PageImpl<>(List.of(hotelRoom)));
        when(hotelRoomMapper.toRoomResponse(any(HotelRoom.class))).thenReturn(roomResponse);

        PagedResponse<RoomResponse> actual = roomService.getRoomsByHotelId(1L, pageable);

        assertNotNull(actual);
        assertEquals(1, actual.pageSize());
        assertEquals(1, actual.totalElements());
        assertEquals(1, actual.totalPages());
        assertEquals(0, actual.pageNumber());
        assertTrue(actual.last());
        assertEquals(actual.content().getFirst(), roomResponse);
        verify(hotelResolver).resolveById(1L);
        verify(hotelRoomRepository).findAllByHotelId(1L, pageable);
        verify(hotelRoomMapper).toRoomResponse(any(HotelRoom.class));
    }

    @Test
    void getRoomById_shouldReturnRoomResponse() {
        HotelRoom hotelRoom = getHotelRoom();

        RoomResponse roomResponse = new RoomResponse(1L, 1L, 1L, "Test Room Number", 5, HotelRoomStatus.FREE, BigDecimal.valueOf(150));

        when(hotelRoomResolver.resolveById(any(Long.class))).thenReturn(hotelRoom);
        when(hotelRoomMapper.toRoomResponse(any(HotelRoom.class))).thenReturn(roomResponse);

        RoomResponse actual = roomService.getRoomById(1L);

        assertNotNull(actual);
        assertEquals(actual, roomResponse);
        verify(hotelRoomResolver).resolveById(1L);
        verify(hotelRoomMapper).toRoomResponse(any(HotelRoom.class));
    }

    @Test
    void createHotelRoom_shouldReturnRoomResponse() {
        HotelRoom hotelRoom = getHotelRoom();

        RoomResponse roomResponse = new RoomResponse(1L, 1L, 1L, "Test Room Number", 5, HotelRoomStatus.FREE, BigDecimal.valueOf(150));

        when(hotelResolver.resolveById(1L)).thenReturn(hotelRoom.getHotel());
        when(roomTypeResolver.resolveById(1L)).thenReturn(hotelRoom.getRoomType());
        when(hotelRoomMapper.toHotelRoom(any(CreateRoomRequest.class), any(Hotel.class), any(RoomType.class))).thenReturn(hotelRoom);
        when(hotelRoomMapper.toRoomResponse(any(HotelRoom.class))).thenReturn(roomResponse);
        when(hotelRoomRepository.save(any(HotelRoom.class))).thenReturn(hotelRoom);

        CreateRoomRequest roomRequest = new CreateRoomRequest();
        roomRequest.setHotelId(1L);
        roomRequest.setRoomTypeId(1L);
        roomRequest.setFloor(5);
        roomRequest.setPricePerNight(BigDecimal.valueOf(150));
        roomRequest.setNumber("Test Room Number");
        roomRequest.setStatus(HotelRoomStatus.FREE);

        RoomResponse actual = roomService.createRoom(roomRequest);

        assertNotNull(actual);
        assertEquals(actual, roomResponse);
        verify(hotelRoomRepository).save(any(HotelRoom.class));
        verify(hotelRoomMapper).toRoomResponse(any(HotelRoom.class));
    }

    @Test
    void updateHotelRoom_shouldReturnRoomResponse() {
        HotelRoom hotelRoom = getHotelRoom();

        RoomResponse roomResponse = new RoomResponse(1L, 1L, 1L, "Test Room Number", 5, HotelRoomStatus.FREE, BigDecimal.valueOf(150));

        when(hotelRoomResolver.resolveById(1L)).thenReturn(hotelRoom);
        when(roomTypeUpdateResolver.resolve(any(UpdateRoomRequest.class), any(HotelRoom.class))).thenReturn(hotelRoom.getRoomType());
        when(hotelRoomMapper.toRoomResponse(any(HotelRoom.class))).thenReturn(roomResponse);

        UpdateRoomRequest roomRequest = new UpdateRoomRequest();
        roomRequest.setStatus(HotelRoomStatus.BUSY);
        roomRequest.setPricePerNight(BigDecimal.valueOf(150));

        RoomResponse actual = roomService.updateRoom(1L, roomRequest);

        assertNotNull(actual);
        assertEquals(actual, roomResponse);
        verify(hotelRoomMapper).toRoomResponse(any(HotelRoom.class));
    }

    private HotelRoom getHotelRoom() {
        Hotel hotel = getHotel();

        RoomType roomType = new RoomType();
        roomType.setId(1L);
        roomType.setArea(500);
        roomType.setDescription("Test Room Type");
        roomType.setName(RoomCategory.DELUXE);
        roomType.setBedType(BedType.DOUBLE);
        roomType.setMaxGuests(5);

        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setId(1L);
        hotelRoom.setHotel(hotel);
        hotelRoom.setStatus(HotelRoomStatus.FREE);
        hotelRoom.setNumber("Test Room Number");
        hotelRoom.setFloor(5);
        hotelRoom.setRoomType(roomType);
        hotelRoom.setPricePerNight(BigDecimal.valueOf(150));

        return hotelRoom;
    }

    private static Hotel getHotel() {
        Location location = new Location();
        location.setCountry("Test Country");
        location.setCity("Test City");
        location.setStreet("Test Street");
        location.setBuilding("Test Building");
        location.setZipCode("Test Zip Code");

        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setStars(5);
        hotel.setStatus(HotelStatus.PLANNED);
        hotel.setLocation(location);
        return hotel;
    }
}
