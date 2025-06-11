package com.hbs.managamentservice.unit.resolver;

import com.hbs.managamentservice.exception.domain.hotel.AmenityNotFoundException;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.repository.AmenityRepository;
import com.hbs.managamentservice.resolver.AmenityResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmenityResolverTest {

    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private AmenityResolver amenityResolver;

    @Test
    void resolveById_shouldReturnAmenity() {
        Long id = 1L;
        Amenity amenity = new Amenity();
        amenity.setId(id);
        when(amenityRepository.findById(id)).thenReturn(Optional.of(amenity));

        Amenity result = amenityResolver.resolveById(id);

        assertEquals(amenity, result);
    }

    @Test
    void resolveByIds_shouldReturnAmenities() {
        Set<Long> ids = Set.of(1L, 2L);
        Amenity amenity = new Amenity();
        amenity.setId(1L);

        Amenity amenity2 = new Amenity();
        amenity.setId(2L);

        List<Amenity> amenities = List.of(amenity, amenity2);
        when(amenityRepository.findAllById(ids)).thenReturn(amenities);

        Set<Amenity> result = amenityResolver.resolveByIds(ids);

        assertEquals(new HashSet<>(amenities), result);
    }

    @Test
    void resolveById_shouldThrowNotFound() {
        Long id = 1L;
        when(amenityRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AmenityNotFoundException.class, () -> amenityResolver.resolveById(id));
    }
}
