package com.hbs.managamentservice.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.exception.domain.hotel.HotelNotFoundException;
import com.hbs.managamentservice.exception.domain.hotel.PhotoNotFoundException;
import com.hbs.managamentservice.mapper.HotelMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelPhoto;
import com.hbs.managamentservice.repository.HotelPhotoRepository;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.service.storage.PresignedUrlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.net.URI;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;
    private final PresignedUrlProvider presignedUrlProvider;
    private final HotelPhotoRepository photoRepository;

    @Value("${storage.s3.url-expiration-seconds:900}")
    private int urlExpirationSeconds;

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
    public URI generatePresignedUrlForPhoto(Long photoId) {
        HotelPhoto photo = photoRepository.findById(photoId).orElseThrow(PhotoNotFoundException::new);
        return URI.create(presignedUrlProvider.generatePresignedUrl(photo.getS3Key(), Duration.ofSeconds(urlExpirationSeconds)).toString());
    }
}