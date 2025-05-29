package com.hbs.managamentservice.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;

public interface HotelService {
    HotelResponse createHotel(CreateHotelRequest hotel);
}
