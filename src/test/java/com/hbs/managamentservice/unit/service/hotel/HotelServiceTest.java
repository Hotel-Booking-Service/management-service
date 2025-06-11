package com.hbs.managamentservice.unit.service.hotel;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.request.LocationRequest;
import com.hbs.managamentservice.dto.request.UpdateHotelRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.LocationResponse;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.mapper.HotelMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelStatus;
import com.hbs.managamentservice.model.Location;
import com.hbs.managamentservice.repository.HotelRepository;
import com.hbs.managamentservice.resolver.HotelRelationResolver;
import com.hbs.managamentservice.resolver.HotelResolver;
import com.hbs.managamentservice.service.hotel.HotelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @Mock
    private HotelResolver hotelResolver;

    @Mock
    private HotelRelationResolver hotelRelationResolver;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void updateHotel_shouldUpdateAllFieldsSuccessfully() {
        Long hotelId = 1L;

        UpdateHotelRequest request = new UpdateHotelRequest();
        request.setName("Updated Hotel");
        request.setDescription("Updated Desc");
        request.setStars(4);
        request.setStatus(HotelStatus.ACTIVE);
        request.setLocationId(10L);
        request.setManagerId(20L);
        request.setAmenityIds(Set.of(100L, 200L));

        Hotel hotel = new Hotel();

        HotelResponse hotelResponse = HotelResponse.builder()
                .id(hotelId)
                .name("Updated Hotel")
                .description("Updated Desc")
                .build();

        when(hotelResolver.resolveById(hotelId)).thenReturn(hotel);
        doNothing().when(hotelRelationResolver).resolveRelations(request, hotel);
        when(hotelMapper.toHotelResponse(hotel)).thenReturn(hotelResponse);

        HotelResponse actual = hotelService.updateHotel(hotelId, request);

        assertNotNull(actual);
        assertEquals(hotelResponse.name(), actual.name());

        verify(hotelResolver).resolveById(hotelId);
        verify(hotelMapper).updateHotelFromPatchRequest(request, hotel);
        verify(hotelMapper).toHotelResponse(hotel);
    }

    @Test
    void getAllHotels_shouldReturnHotelResponseList() {
        Hotel hotel = getHotel();

        LocationResponse locationResponse = new LocationResponse("Test Country", "Test City", "Test Street", "Test Building", "Test Zip Code");
        HotelResponse hotelResponse = new HotelResponse(1L, "Test Hotel", "Test Description", 5, HotelStatus.PLANNED, locationResponse, List.of(), false, null);

        Pageable pageable = PageRequest.of(0, 10);

        when(hotelRepository.findAllByDeletedFalse(pageable)).thenReturn(new PageImpl<>(List.of(hotel)));
        when(hotelMapper.toHotelResponse(any(Hotel.class))).thenReturn(hotelResponse);

        PagedResponse<HotelResponse> actual = hotelService.getAllHotels(pageable);

        assertNotNull(actual);
        assertEquals(1, actual.pageSize());
        assertEquals(1, actual.totalElements());
        assertEquals(1, actual.totalPages());
        assertEquals(0, actual.pageNumber());
        assertTrue(actual.last());
        assertEquals(actual.content().getFirst(), hotelResponse);
        verify(hotelRepository).findAllByDeletedFalse(pageable);
    }

    @Test
    void getHotelById_shouldReturnHotelResponse() {
        Hotel hotel = getHotel();

        LocationResponse locationResponse = new LocationResponse("Test Country", "Test City", "Test Street", "Test Building", "Test Zip Code");
        HotelResponse hotelResponse = new HotelResponse(1L, "Test Hotel", "Test Description", 5, HotelStatus.PLANNED, locationResponse, List.of(), false, null);

        when(hotelMapper.toHotelResponse(any(Hotel.class))).thenReturn(hotelResponse);
        when(hotelResolver.resolveById(any(Long.class))).thenReturn(hotel);

        HotelResponse actual = hotelService.getHotelById(1L);

        assertNotNull(actual);
        assertEquals(actual, hotelResponse);
        verify(hotelRepository).findById(1L);
    }

    @Test
    void createHotel_shouldReturnHotelResponse() {
        CreateHotelRequest request = getCreateHotelRequest();

        Hotel hotel = getHotel();

        LocationResponse locationResponse = new LocationResponse("Test Country", "Test City", "Test Street", "Test Building", "Test Zip Code");
        HotelResponse hotelResponse = new HotelResponse(1L, "Test Hotel", "Test Description", 5, HotelStatus.PLANNED, locationResponse, List.of(), false, null);

        when(hotelMapper.toEntity(any(CreateHotelRequest.class))).thenReturn(hotel);
        when(hotelMapper.toHotelResponse(any(Hotel.class))).thenReturn(hotelResponse);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        HotelResponse actual = hotelService.createHotel(request);

        assertNotNull(hotel);
        assertEquals(actual, hotelResponse);
        verify(hotelRepository).save(any(Hotel.class));
        verify(hotelMapper).toHotelResponse(hotel);
    }


    @Test
    void deleteHotel_shouldDeleteHotel() {
        Hotel hotel = getHotel();

        when(hotelResolver.resolveById(any(Long.class))).thenReturn(hotel);

        hotelService.deleteHotel(1L);

        verify(hotelRepository).findById(1L);
        verify(hotelRepository).save(hotel);
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
