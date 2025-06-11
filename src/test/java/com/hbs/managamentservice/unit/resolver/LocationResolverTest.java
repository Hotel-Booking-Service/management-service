package com.hbs.managamentservice.unit.resolver;

import com.hbs.managamentservice.exception.domain.hotel.LocationNotFoundException;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.repository.LocationRepository;
import com.hbs.managamentservice.resolver.LocationResolver;
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
class LocationResolverTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationResolver locationResolver;

    @Test
    void resolveById_shouldReturnManager() {
        Long id = 1L;
        Location location = new Location();
        location.setId(id);
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        Location result = locationResolver.resolveById(id);

        assertEquals(location, result);
    }

    @Test
    void resolveById_shouldThrowNotFound() {
        Long id = 1L;
        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(LocationNotFoundException.class, () -> locationResolver.resolveById(id));
    }
}
