package com.hbs.managamentservice.unit.resolver;

import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.resolver.AmenityResolver;
import com.hbs.managamentservice.resolver.HotelRelationResolver;
import com.hbs.managamentservice.resolver.LocationResolver;
import com.hbs.managamentservice.resolver.ManagerResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelRelationResolverTest {

    @Mock
    private LocationResolver locationResolver;

    @Mock
    private ManagerResolver managerResolver;

    @Mock
    private AmenityResolver amenityResolver;

    @InjectMocks
    private HotelRelationResolver relationResolver;

    @Mock
    private UpdateHotelRequest request;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
    }

    @Test
    void resolveRelations_shouldSetLocation_whenLocationIdIsPresent() {
        Long locationId = 1L;
        Location location = new Location();

        when(request.getLocationId()).thenReturn(locationId);
        when(locationResolver.resolveById(locationId)).thenReturn(location);
        when(request.getManagerId()).thenReturn(null);
        when(request.getAmenityIds()).thenReturn(null);

        relationResolver.resolveRelations(request, hotel);

        assertEquals(location, hotel.getLocation());
        verify(locationResolver).resolveById(locationId);
    }

    @Test
    void resolveRelations_shouldSetManager_whenManagerIdIsPresent() {
        Long managerId = 2L;
        Manager manager = new Manager();

        when(request.getLocationId()).thenReturn(null);
        when(request.getManagerId()).thenReturn(managerId);
        when(managerResolver.resolveById(managerId)).thenReturn(manager);
        when(request.getAmenityIds()).thenReturn(null);

        relationResolver.resolveRelations(request, hotel);

        assertEquals(manager, hotel.getManager());
        verify(managerResolver).resolveById(managerId);
    }

    @Test
    void resolveRelations_shouldSetAmenities_whenAmenityIdsArePresent() {
        Set<Long> amenityIds = Set.of(10L, 20L);
        Set<Amenity> amenities = Set.of(new Amenity(), new Amenity());

        when(request.getLocationId()).thenReturn(null);
        when(request.getManagerId()).thenReturn(null);
        when(request.getAmenityIds()).thenReturn(amenityIds);
        when(amenityResolver.resolveByIds(amenityIds)).thenReturn(amenities);

        relationResolver.resolveRelations(request, hotel);

        assertEquals(amenities, hotel.getAmenities());
        verify(amenityResolver).resolveByIds(amenityIds);
    }

    @Test
    void resolveRelations_shouldDoNothing_whenAllIdsAreNull() {
        when(request.getLocationId()).thenReturn(null);
        when(request.getManagerId()).thenReturn(null);
        when(request.getAmenityIds()).thenReturn(null);

        relationResolver.resolveRelations(request, hotel);

        verifyNoInteractions(locationResolver, managerResolver, amenityResolver);
        assertNull(hotel.getLocation());
        assertNull(hotel.getManager());
        assertEquals(Set.of(), hotel.getAmenities());
    }
}
