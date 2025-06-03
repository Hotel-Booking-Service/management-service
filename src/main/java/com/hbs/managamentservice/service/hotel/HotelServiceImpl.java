package com.hbs.managamentservice.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.exception.domain.hotel.HotelNotFoundException;
import com.hbs.managamentservice.mapper.HotelMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.validation.HotelEntityFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;
    private final HotelEntityFetcher hotelFetcher;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<HotelResponse> getAllHotels(Pageable pageable) {
        Page<Hotel> hotelsPage = hotelRepository.findAll(pageable);
        List<HotelResponse> content = hotelsPage.stream()
                .map(hotelMapper::toHotelResponse)
                .toList();

        return new PagedResponse<>(
                content,
                hotelsPage.getNumber(),
                hotelsPage.getSize(),
                hotelsPage.getTotalElements(),
                hotelsPage.getTotalPages(),
                hotelsPage.isLast()
        );
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

    @Override
    @Transactional
    public HotelResponse patchHotel(Long id, UpdateHotelRequest request) {

        Hotel hotel = hotelFetcher.fetchHotel(id);

        if (request.getLocationId() != null) {
            hotel.setLocation(hotelFetcher.fetchLocation(request.getLocationId()));
        }

        if(request.getManagerId() != null) {
            hotel.setManager(hotelFetcher.fetchManager(request.getManagerId()));
        }

        if(request.getAmenityIds() != null) {
            hotel.setAmenities(hotelFetcher.fetchAmenities(request.getAmenityIds()));
        }

        if(request.getAmenityIds() != null) {
            hotel.setPhotos(hotelFetcher.fetchPhotos(request.getPhotoIds()));
        }
        hotelMapper.updateHotelFromPatchRequest(request, hotel);

        hotelRepository.save(hotel);

        return hotelMapper.toHotelResponse(hotel);
    }

}