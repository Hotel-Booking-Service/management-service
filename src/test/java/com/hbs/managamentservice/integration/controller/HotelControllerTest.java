package com.hbs.managamentservice.integration.controller;

import com.hbs.managamentservice.dto.request.CreateHotelRequest;
import com.hbs.managamentservice.dto.request.LocationRequest;
import com.hbs.managamentservice.dto.response.HotelResponse;
import com.hbs.managamentservice.dto.response.LocationResponse;
import com.hbs.managamentservice.model.HotelStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testCreateHotel() {
        CreateHotelRequest request = getCreateHotelRequest();

        ResponseEntity<HotelResponse> response = testRestTemplate.postForEntity(
                "/api/v1/hotels",
                request,
                HotelResponse.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        HotelResponse hotelResponse = response.getBody();
        assertNotNull(hotelResponse);
        assertThat(hotelResponse.id()).isNotNull().isPositive();

        HotelResponse expected = new HotelResponse(
                hotelResponse.id(),
                "Grand Plaza",
                "Современный отель в центре города с высоким уровнем сервиса.",
                5,
                HotelStatus.ACTIVE,
                new LocationResponse("Estonia", "Tallinn", "Pikk Street", "12A", "10123"),
                List.of()
        );

        assertThat(hotelResponse)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    private static CreateHotelRequest getCreateHotelRequest() {
        LocationRequest locationRequest = new LocationRequest(
                "Estonia",
                "Tallinn",
                "Pikk Street",
                "12A",
                "10123"
        );

        return new CreateHotelRequest(
                "Grand Plaza",
                "Современный отель в центре города с высоким уровнем сервиса.",
                5,
                HotelStatus.ACTIVE,
                locationRequest                                     // location
        );
    }
}
