package com.hbs.managamentservice.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.exception.domain.hotel.HotelNotFoundException;
import com.hbs.managamentservice.mapper.HotelMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<HotelResponse> getAllHotels(Pageable pageable) {
        Page<Hotel> hotelsPage = hotelRepository.findAll(pageable);

        return PagedResponse.from(hotelsPage, hotelMapper::toHotelResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public HotelResponse getHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(hotelMapper::toHotelResponse)
                .orElseThrow(HotelNotFoundException::new);
    }

    @Override
    @Transactional
    public HotelResponse createHotel(CreateHotelRequest hotel) {
        Hotel hotelEntity = hotelMapper.toEntity(hotel);
        hotelEntity = hotelRepository.save(hotelEntity);
        return hotelMapper.toHotelResponse(hotelEntity);
    }

}