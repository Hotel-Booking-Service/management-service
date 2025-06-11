package com.hbs.managamentservice.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.mapper.HotelMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.resolver.HotelRelationResolver;
import com.hbs.managamentservice.resolver.HotelResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;
    private final HotelRelationResolver hotelRelationResolver;
    private final HotelResolver hotelResolver;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<HotelResponse> getAllHotels(Pageable pageable) {
        Page<Hotel> hotelsPage = hotelRepository.findAllByDeletedFalse(pageable);

        return PagedResponse.from(hotelsPage, hotelMapper::toHotelResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelResolver.resolveById(id);
        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    @Transactional
    public HotelResponse createHotel(CreateHotelRequest hotel) {
        Hotel hotelEntity = hotelMapper.toEntity(hotel);
        hotelEntity = hotelRepository.save(hotelEntity);
        return hotelMapper.toHotelResponse(hotelEntity);
    }

    @Override
    @Transactional
    public HotelResponse updateHotel(Long id, UpdateHotelRequest request) {
        Hotel hotel = hotelResolver.resolveById(id);

        hotelRelationResolver.resolveRelations(request, hotel);

        hotelMapper.updateHotelFromPatchRequest(request, hotel);

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    @Transactional
    public void deleteHotel(Long id) {
        Hotel hotel = hotelResolver.resolveById(id);
        hotel.setDeleted(true);
        hotel.setDeletedAt(LocalDateTime.now());
        hotelRepository.save(hotel);
    }
}