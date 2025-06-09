package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.exception.domain.hotel.HotelNotFoundException;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HotelResolver implements EntityResolver<Hotel> {

    private final HotelRepository hotelRepository;

    @Override
    @Transactional(readOnly = true)
    public Hotel resolveById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(HotelNotFoundException::new);
    }
}
