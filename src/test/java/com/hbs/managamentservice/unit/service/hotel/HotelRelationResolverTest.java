package com.hbs.managamentservice.unit.service.hotel;

import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.model.Amenity;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.model.Manager;
import com.hbs.managamentservice.service.hotel.HotelRelationResolver;
import com.hbs.managamentservice.validation.HotelEntityFetcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelRelationResolverTest {

    @Mock
    private HotelEntityFetcher hotelFetcher;

    @InjectMocks
    private HotelRelationResolver hotelRelationResolver;

    @Test
    void resolveRelations_shouldSetAllRelations() {
        Hotel hotel = new Hotel();
        UpdateHotelRequest request = new UpdateHotelRequest();
        request.setLocationId(1L);
        request.setManagerId(2L);
        request.setAmenityIds(Set.of(10L, 20L));

        Location location = new Location();
        Manager manager = new Manager();
        Set<Amenity> amenities = Set.of(new Amenity(), new Amenity());

        when(hotelFetcher.fetchLocation(1L)).thenReturn(location);
        when(hotelFetcher.fetchManager(2L)).thenReturn(manager);
        when(hotelFetcher.fetchAmenities(Set.of(10L, 20L))).thenReturn(amenities);

        hotelRelationResolver.resolveRelations(hotel, request);

        assertEquals(location, hotel.getLocation());
        assertEquals(manager, hotel.getManager());
        assertEquals(amenities, hotel.getAmenities());

        verify(hotelFetcher).fetchLocation(1L);
        verify(hotelFetcher).fetchManager(2L);
        verify(hotelFetcher).fetchAmenities(Set.of(10L, 20L));
    }

    @Test
    void resolveRelations_shouldSkipNullFields() {
        Hotel hotel = new Hotel();
        UpdateHotelRequest request = new UpdateHotelRequest();
        request.setLocationId(null);
        request.setManagerId(null);
        request.setAmenityIds(null);

        hotelRelationResolver.resolveRelations(hotel, request);

        assertNull(hotel.getLocation());
        assertNull(hotel.getManager());
        assertTrue(hotel.getAmenities() == null || hotel.getAmenities().isEmpty());

        verifyNoInteractions(hotelFetcher);
    }

    @Test
    void resolveRelations_shouldHandlePartialInput() {
        Hotel hotel = new Hotel();
        UpdateHotelRequest request = new UpdateHotelRequest();
        request.setLocationId(5L);

        Location location = new Location();
        when(hotelFetcher.fetchLocation(5L)).thenReturn(location);

        hotelRelationResolver.resolveRelations(hotel, request);

        assertEquals(location, hotel.getLocation());
        assertNull(hotel.getManager());
        assertTrue(hotel.getAmenities() == null || hotel.getAmenities().isEmpty());

        verify(hotelFetcher).fetchLocation(5L);
        verifyNoMoreInteractions(hotelFetcher);
    }

}
