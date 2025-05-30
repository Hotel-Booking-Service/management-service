package com.hbs.managamentservice.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.mapper.HotelMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    public HotelResponse createHotel(CreateHotelRequest hotel) {
        Hotel hotelEntity = hotelMapper.toEntity(hotel);
        hotelEntity = hotelRepository.save(hotelEntity);
        return hotelMapper.toHotelResponse(hotelEntity);
    }
}