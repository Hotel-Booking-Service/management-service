package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.exception.domain.hotel.LocationNotFoundException;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LocationResolver implements EntityResolver<Location> {

    private final LocationRepository locationRepository;

    @Override
    @Transactional(readOnly = true)
    public Location resolveById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(LocationNotFoundException::new);
    }
}
