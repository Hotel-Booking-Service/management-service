package com.hbs.managamentservice.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface HotelService {

    PagedResponse<HotelResponse> getAllHotels(Pageable pageable);

    HotelResponse getHotelById(Long id);

    HotelResponse createHotel(CreateHotelRequest hotel);

    HotelResponse patchHotel(Long id, UpdateHotelRequest request);

}
