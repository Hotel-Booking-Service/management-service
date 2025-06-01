package com.hbs.managamentservice.controller.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.service.hotel.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotels")
public class HotelControllerImpl implements HotelController {

    private final HotelService hotelService;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<HotelResponse> getAllHotels(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return hotelService.getAllHotels(pageable);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse createHotel(@RequestBody @Valid CreateHotelRequest hotel) {
        return hotelService.createHotel(hotel);
    }

    @Override
    @GetMapping("/photos/{photoId}")
    public ResponseEntity<Void> getPhotoById(@PathVariable("photoId") Long photoId) {
        URI presignedURI = hotelService.generatePresignedUrlForPhoto(photoId);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(presignedURI).build();
    }
}
