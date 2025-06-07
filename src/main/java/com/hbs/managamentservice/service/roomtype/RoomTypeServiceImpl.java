package com.hbs.managamentservice.service.roomtype;

import com.hbs.managamentservice.dto.request.RoomTypeRequest;
import com.hbs.managamentservice.dto.response.RoomTypeResponse;
import com.hbs.managamentservice.exception.domain.roomtype.RoomTypeNotFoundException;
import com.hbs.managamentservice.mapper.RoomTypeMapper;
import com.hbs.managamentservice.model.RoomType;
import com.hbs.managamentservice.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeMapper roomTypeMapper;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public RoomTypeResponse getRoomTypeById(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(RoomTypeNotFoundException::new);
        return roomTypeMapper.toRoomTypeResponse(roomType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomTypeResponse> getAllRoomTypes() {
        return roomTypeRepository.findAll().stream()
                .map(roomTypeMapper::toRoomTypeResponse)
                .toList();
    }

    @Override
    @Transactional
    public RoomTypeResponse createRoomType(RoomTypeRequest roomTypeRequest) {
        RoomType roomType = roomTypeMapper.toRoomType(roomTypeRequest);
        roomType = roomTypeRepository.save(roomType);
        return roomTypeMapper.toRoomTypeResponse(roomType);
    }

    @Override
    @Transactional
    public RoomTypeResponse updateRoomType(Long id, RoomTypeRequest roomTypeDto) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(RoomTypeNotFoundException::new);
        roomTypeMapper.updateRoomType(roomTypeDto, roomType);
        return roomTypeMapper.toRoomTypeResponse(roomType);
    }

    @Override
    @Transactional
    public void deleteRoomType(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(RoomTypeNotFoundException::new);
        roomTypeRepository.delete(roomType);
    }
}
