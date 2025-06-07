package com.hbs.managamentservice.integration.controller;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.hbs.managamentservice.dto.request.RoomTypeRequest;
import com.hbs.managamentservice.dto.response.RoomTypeResponse;
import com.hbs.managamentservice.model.BedType;
import com.hbs.managamentservice.model.RoomCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@DBUnit(caseSensitiveTableNames = true, schema = "public")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomTypeControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DataSet(value = {"dataset/roomtype/room-types.yaml"}, cleanBefore = true)
    void testGetAllRoomTypes() {
        ResponseEntity<List<RoomTypeResponse>> response = testRestTemplate.exchange(
                "/api/v1/room-types",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @DataSet(value = {"dataset/roomtype/room-types.yaml"}, cleanBefore = true)
    void testGetRoomType() {
        ResponseEntity<RoomTypeResponse> response = testRestTemplate.getForEntity(
                "/api/v1/room-types/1",
                RoomTypeResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertThat(response.getBody().id()).isEqualTo(1);
    }

    @Test
    @DataSet(cleanBefore = true)
    void testCreateRoomType() {
        RoomTypeRequest roomTypeRequest = new RoomTypeRequest();
        roomTypeRequest.setName(RoomCategory.STANDARD);
        roomTypeRequest.setDescription("Description");
        roomTypeRequest.setBedType(BedType.DOUBLE);
        roomTypeRequest.setArea(150.0);
        roomTypeRequest.setMaxGuests(5);

        ResponseEntity<RoomTypeResponse> response = testRestTemplate.postForEntity(
                "/api/v1/room-types",
                roomTypeRequest,
                RoomTypeResponse.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(response.getBody());
        assertThat(response.getBody().id()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo(RoomCategory.STANDARD);
        assertThat(response.getBody().description()).isEqualTo("Description");
        assertThat(response.getBody().bedType()).isEqualTo(BedType.DOUBLE);
        assertThat(response.getBody().area()).isEqualTo(150.0);
        assertThat(response.getBody().maxGuests()).isEqualTo(5);
    }

    @Test
    @DataSet(value = {"dataset/roomtype/room-types.yaml"}, cleanBefore = true)
    void testUpdateRoomType() {
        RoomTypeRequest roomTypeRequest = new RoomTypeRequest();
        roomTypeRequest.setName(RoomCategory.STANDARD);
        roomTypeRequest.setDescription("Description");
        roomTypeRequest.setBedType(BedType.DOUBLE);
        roomTypeRequest.setArea(150.0);
        roomTypeRequest.setMaxGuests(5);

        ResponseEntity<RoomTypeResponse> response = testRestTemplate.exchange(
                "/api/v1/room-types/1",
                HttpMethod.PUT,
                new HttpEntity<>(roomTypeRequest),
                RoomTypeResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertThat(response.getBody().id()).isEqualTo(1);
        assertThat(response.getBody().name()).isEqualTo(RoomCategory.STANDARD);
        assertThat(response.getBody().description()).isEqualTo("Description");
        assertThat(response.getBody().bedType()).isEqualTo(BedType.DOUBLE);
        assertThat(response.getBody().area()).isEqualTo(150.0);
        assertThat(response.getBody().maxGuests()).isEqualTo(5);
    }

    @Test
    @DataSet(value = {"dataset/roomtype/room-types.yaml"}, cleanBefore = true)
    void testDeleteRoomType() {
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/api/v1/room-types/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
