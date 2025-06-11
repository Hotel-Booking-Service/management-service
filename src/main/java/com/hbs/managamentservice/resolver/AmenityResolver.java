package com.hbs.managamentservice.resolver;

import com.hbs.managamentservice.exception.domain.hotel.AmenityNotFoundException;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AmenityResolver implements EntityResolver<Amenity> {

    private final AmenityRepository amenityRepository;

    @Override
    @Transactional(readOnly = true)
    public Amenity resolveById(Long id) {
        return amenityRepository.findById(id)
                .orElseThrow(AmenityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Set<Amenity> resolveByIds(Set<Long> ids) {
        List<Amenity> amenities = amenityRepository.findAllById(ids);
        if(amenities.size() != ids.size()) {
            throw new AmenityNotFoundException();
        }
        return new HashSet<>(amenities);
    }
}
