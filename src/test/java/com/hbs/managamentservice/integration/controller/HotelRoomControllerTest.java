package com.hbs.managamentservice.integration.controller;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.hbs.managamentservice.dto.response.PagedResponse;
import com.hbs.managamentservice.dto.response.RoomResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@DBUnit(caseSensitiveTableNames = true, schema = "public")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelRoomControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DataSet(value = {"dataset/location/locations.yaml", "dataset/hotel/hotels.yaml", "dataset/roomtype/room-types.yaml", "dataset/room/rooms.yaml"}, cleanBefore = true)
    void testGetAllHotelRooms() {
        testRestTemplate.getForEntity("/api/v1/hotels/1/rooms", String.class);

        ResponseEntity<PagedResponse<RoomResponse>> response = testRestTemplate.exchange(
                "/api/v1/hotels/1/rooms?page=0&size=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PagedResponse<RoomResponse> hotels = response.getBody();
        assertNotNull(hotels);
        assertThat(hotels.content()).isNotEmpty();
    }

    @Test
    @DataSet(value = {"dataset/location/locations.yaml", "dataset/hotel/hotels.yaml", "dataset/roomtype/room-types.yaml", "dataset/room/rooms.yaml"}, cleanBefore = true)
    void testGetHotelRoomsByHotelId() {
        ResponseEntity<RoomResponse> response = testRestTemplate.getForEntity(
                "/api/v1/rooms/1",
                RoomResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        RoomResponse hotelResponse = response.getBody();
        assertNotNull(hotelResponse);
        assertThat(hotelResponse.id()).isEqualTo(1);
    }
}
