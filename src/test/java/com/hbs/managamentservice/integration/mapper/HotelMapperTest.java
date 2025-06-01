package com.hbs.managamentservice.integration.mapper;

import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.mapper.HotelMapper;
import com.hbs.managamentservice.model.Hotel;
import com.hbs.managamentservice.model.HotelPhoto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ActiveProfiles("test-no-docker")
@SpringBootTest
class HotelMapperTest {

    @Autowired
    private HotelMapper hotelMapper;

    @Test
    void toHotelResponse_shouldMapHotelToResponseWithPhotoUris() {
        HotelPhoto photo = new HotelPhoto();
        photo.setS3Key("photo.jpg");

        Set<HotelPhoto> photos = new HashSet<>();
        photos.add(photo);

        Hotel hotel = new Hotel();
        hotel.setPhotos(photos);

        try (var builderMock = mockStatic(ServletUriComponentsBuilder.class)) {
            ServletUriComponentsBuilder mockBuilder = mock(ServletUriComponentsBuilder.class);
            UriComponents mockUri = mock(UriComponents.class);

            builderMock.when(ServletUriComponentsBuilder::fromCurrentContextPath).thenReturn(mockBuilder);
            when(mockBuilder.path("/api/v1/hotels/photos/{id}")).thenReturn(mockBuilder);
            when(mockBuilder.buildAndExpand("photo.jpg")).thenReturn(mockUri);
            when(mockUri.toUri()).thenReturn(URI.create("http://localhost/api/v1/hotels/photos/photo.jpg"));

            HotelResponse response = hotelMapper.toHotelResponse(hotel);

            assertNotNull(response);
            List<URI> uris = response.photos();
            assertEquals(1, uris.size());
            assertEquals("http://localhost/api/v1/hotels/photos/photo.jpg", uris.getFirst().toString());
        }
    }
}
