package com.hbs.managamentservice.unit.service.roomtype;

import com.hbs.managamentservice.dto.request.RoomTypeRequest;
import com.hbs.managamentservice.dto.response.RoomTypeResponse;
import com.hbs.managamentservice.exception.domain.roomtype.RoomTypeNotFoundException;
import com.hbs.managamentservice.mapper.RoomTypeMapper;
import com.hbs.managamentservice.model.BedType;
import com.hbs.managamentservice.model.RoomCategory;
import com.hbs.managamentservice.model.RoomType;
import com.hbs.managamentservice.repository.RoomTypeRepository;
import com.hbs.managamentservice.service.roomtype.RoomTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomTypeServiceTest {

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private RoomTypeMapper roomTypeMapper;

    @InjectMocks
    private RoomTypeServiceImpl roomTypeService;

    @Test
    void getRoomTypeById_shouldReturnRoomTypeResponse() {
        RoomType roomType = getRoomType();

        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomType));
        when(roomTypeMapper.toRoomTypeResponse(roomType)).thenReturn(getRoomTypeResponse());

        RoomTypeResponse roomTypeResponse = roomTypeService.getRoomTypeById(1L);

        assertNotNull(roomTypeResponse);
        assertEquals(roomTypeResponse, getRoomTypeResponse());

        verify(roomTypeRepository, times(1)).findById(1L);
    }

    @Test
    void getRoomTypeById_shouldReturnNull_whenRoomTypeNotFound() {
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomTypeNotFoundException.class, () -> roomTypeService.getRoomTypeById(1L));
    }

    @Test
    void getAllRoomTypes_shouldReturnRoomTypeResponseList() {
        RoomType roomType = getRoomType();

        when(roomTypeRepository.findAll()).thenReturn(List.of(roomType));
        when(roomTypeMapper.toRoomTypeResponse(roomType)).thenReturn(getRoomTypeResponse());

        List<RoomTypeResponse> roomTypeResponseList = roomTypeService.getAllRoomTypes();

        assertNotNull(roomTypeResponseList);
        assertEquals(1, roomTypeResponseList.size());

        verify(roomTypeRepository, times(1)).findAll();
    }

    @Test
    void createRoomType_shouldReturnRoomTypeResponse() {
        RoomType roomType = getRoomType();

        when(roomTypeMapper.toRoomType(getRoomTypeRequest())).thenReturn(roomType);
        when(roomTypeRepository.save(roomType)).thenReturn(roomType);
        when(roomTypeMapper.toRoomTypeResponse(roomType)).thenReturn(getRoomTypeResponse());

        RoomTypeResponse roomTypeResponse = roomTypeService.createRoomType(getRoomTypeRequest());

        assertNotNull(roomTypeResponse);
        assertEquals(roomTypeResponse, getRoomTypeResponse());

        verify(roomTypeRepository, times(1)).save(roomType);
    }

    @Test
    void updateRoomType_shouldUpdateFieldsAndReturnResponse() {
        Long id = 1L;
        RoomTypeRequest request = new RoomTypeRequest();
        request.setName(RoomCategory.SUITE);
        request.setDescription("Updated description");
        request.setBedType(BedType.DOUBLE);
        request.setArea(35.0);
        request.setMaxGuests(4);

        RoomType existing = new RoomType();
        existing.setId(id);
        existing.setName(RoomCategory.STANDARD);
        existing.setDescription("Old desc");
        existing.setBedType(BedType.SINGLE);
        existing.setArea(20.0);
        existing.setMaxGuests(2);

        RoomTypeResponse expectedResponse = new RoomTypeResponse(
                id,
                RoomCategory.SUITE,
                "Updated description",
                BedType.DOUBLE,
                35.0,
                4
        );

        when(roomTypeRepository.findById(id)).thenReturn(Optional.of(existing));

        doAnswer(invocation -> {
            RoomTypeRequest req = invocation.getArgument(0);
            RoomType entity = invocation.getArgument(1);
            entity.setName(req.getName());
            entity.setDescription(req.getDescription());
            entity.setBedType(req.getBedType());
            entity.setArea(req.getArea());
            entity.setMaxGuests(req.getMaxGuests());
            return null;
        }).when(roomTypeMapper).updateRoomType(any(RoomTypeRequest.class), any(RoomType.class));

        when(roomTypeMapper.toRoomTypeResponse(existing)).thenReturn(expectedResponse);

        RoomTypeResponse actual = roomTypeService.updateRoomType(id, request);

        assertNotNull(actual);
        assertEquals(expectedResponse.id(), actual.id());
        assertEquals(expectedResponse.name(), actual.name());
        assertEquals(expectedResponse.description(), actual.description());
        assertEquals(expectedResponse.bedType(), actual.bedType());
        assertEquals(expectedResponse.area(), actual.area(), 0.001);
        assertEquals(expectedResponse.maxGuests(), actual.maxGuests());

        verify(roomTypeRepository).findById(id);
        verify(roomTypeMapper).updateRoomType(request, existing);
        verify(roomTypeMapper).toRoomTypeResponse(existing);
    }

    @Test
    void updateRoomType_shouldThrowExceptionWhenNotFound() {
        Long id = 42L;
        RoomTypeRequest request = new RoomTypeRequest();
        when(roomTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RoomTypeNotFoundException.class, () ->
                roomTypeService.updateRoomType(id, request));

        verify(roomTypeRepository).findById(id);
        verifyNoMoreInteractions(roomTypeMapper);
    }

    @Test
    void deleteRoomType_shouldDeleteRoomType() {
        Long id = 1L;
        RoomType roomType = getRoomType();
        when(roomTypeRepository.findById(id)).thenReturn(Optional.of(roomType));
        roomTypeService.deleteRoomType(id);
        verify(roomTypeRepository).delete(roomType);
    }

    private RoomType getRoomType() {
        RoomType roomType = new RoomType();
        roomType.setId(1L);
        roomType.setName(RoomCategory.STANDARD);
        roomType.setDescription("Single room");
        roomType.setArea(100.0);
        roomType.setBedType(BedType.SINGLE);
        roomType.setMaxGuests(5);
        return roomType;
    }

    private RoomTypeResponse getRoomTypeResponse() {
        return new RoomTypeResponse(1L, RoomCategory.STANDARD, "Single room", BedType.SINGLE, 100.0, 5);
    }

    private RoomTypeRequest getRoomTypeRequest() {
        return new RoomTypeRequest(RoomCategory.STANDARD, "Single room", BedType.SINGLE, 100.0, 5);
    }
}
