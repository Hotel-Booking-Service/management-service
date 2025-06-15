package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.exception.domain.photo.PhotoNotFoundException;
import com.hbs.managamentservice.model.HotelPhoto;
import com.hbs.managamentservice.repository.HotelPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HotelPhotoResolver implements EntityResolver<HotelPhoto> {

    private final HotelPhotoRepository hotelPhotoRepository;

    @Override
    @Transactional(readOnly = true)
    public HotelPhoto resolveById(Long id) {
        return hotelPhotoRepository.findById(id)
                .orElseThrow(PhotoNotFoundException::new);
    }
}
