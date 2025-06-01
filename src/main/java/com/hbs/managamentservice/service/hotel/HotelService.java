package com.hbs.managamentservice.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;

import java.net.URI;

public interface HotelService {
    HotelResponse createHotel(CreateHotelRequest hotel);

    URI generatePresignedUrlForPhoto(Long photoId);
}
