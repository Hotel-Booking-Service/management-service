package com.hbs.managamentservice.unit.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.request.LocationRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.LocationResponse;
import com.hbs.managamentservice.mapper.HotelMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelStatus;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.service.hotel.HotelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void createHotel_shouldReturnHotelResponse() {
        CreateHotelRequest request = getCreateHotelRequest();

        Hotel hotel = getHotel();

        LocationResponse locationResponse = new LocationResponse("Test Country", "Test City", "Test Street", "Test Building", "Test Zip Code");
        HotelResponse hotelResponse = new HotelResponse(1L, "Test Hotel", "Test Description", 5, HotelStatus.PLANNED, locationResponse, List.of());

        when(hotelMapper.toEntity(any(CreateHotelRequest.class))).thenReturn(hotel);
        when(hotelMapper.toHotelResponse(any(Hotel.class))).thenReturn(hotelResponse);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        HotelResponse actual = hotelService.createHotel(request);

        assertNotNull(hotel);
        assertEquals(actual, hotelResponse);
        verify(hotelRepository).save(any(Hotel.class));
        verify(hotelMapper).toHotelResponse(hotel);
    }

    private static CreateHotelRequest getCreateHotelRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setCountry("Test Country");
        locationRequest.setCity("Test City");
        locationRequest.setStreet("Test Street");
        locationRequest.setBuilding("Test Building");
        locationRequest.setZipCode("Test Zip Code");

        CreateHotelRequest request = new CreateHotelRequest();
        request.setName("Test Hotel");
        request.setDescription("Test Description");
        request.setStars(5);
        request.setLocation(locationRequest);
        request.setStatus(HotelStatus.PLANNED);
        return request;
    }

    private static Hotel getHotel() {
        Location location = new Location();
        location.setCountry("Test Country");
        location.setCity("Test City");
        location.setStreet("Test Street");
        location.setBuilding("Test Building");
        location.setZipCode("Test Zip Code");

        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setStars(5);
        hotel.setStatus(HotelStatus.PLANNED);
        hotel.setLocation(location);
        return hotel;
    }
}
