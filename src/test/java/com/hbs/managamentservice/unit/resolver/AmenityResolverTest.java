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

import java.util.Optional;

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
    void resolveById_shouldReturnManager() {
        Long id = 1L;
        Amenity amenity = new Amenity();
        amenity.setId(id);
        when(amenityRepository.findById(id)).thenReturn(Optional.of(amenity));

        Amenity result = amenityResolver.resolveById(id);

        assertEquals(amenity, result);
    }

    @Test
    void resolveById_shouldThrowNotFound() {
        Long id = 1L;
        when(amenityRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AmenityNotFoundException.class, () -> amenityResolver.resolveById(id));
    }
}
