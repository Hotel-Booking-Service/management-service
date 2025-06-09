package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.exception.domain.roomtype.RoomTypeNotFoundException;
import com.hbs.managamentservice.model.RoomType;
import com.hbs.managamentservice.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoomTypeResolver implements EntityResolver<RoomType> {

    private final RoomTypeRepository roomTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public RoomType resolveById(Long id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(RoomTypeNotFoundException::new);
    }
}
