package com.hbs.managamentservice.integration.controller;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.hbs.managamentservice.dto.response.PhotoUploadResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@DBUnit(caseSensitiveTableNames = true, schema = "public")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelPhotoControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DataSet(value = {"dataset/location/locations.yaml", "dataset/hotel/hotels.yaml", "dataset/photo/photos.yaml"}, cleanBefore = true, cleanAfter = true)
    void testGetPhotoById() {
        ResponseEntity<Void> response = testRestTemplate.getForEntity(
                "/api/v1/hotels/photos/1",
                Void.class
        );
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    @DataSet(value = {"dataset/location/locations.yaml", "dataset/hotel/hotels.yaml", "dataset/photo/photos.yaml"}, cleanBefore = true, cleanAfter = true)
    void testGetHotelPhotos() {
        ResponseEntity<List<PhotoUploadResponse>> response = testRestTemplate.exchange(
                "/api/v1/hotels/1/photos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DataSet(value = {"dataset/location/locations.yaml", "dataset/hotel/hotels.yaml"}, cleanBefore = true, cleanAfter = true)
    void testCreateHotelPhotos() {
        Resource resource = new ClassPathResource("dataset/photo/icon.png");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("photos", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                "/api/v1/hotels/1/photos",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        System.out.println(response.getBody());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DataSet(value = {"dataset/location/locations.yaml", "dataset/hotel/hotels.yaml", "dataset/photo/photos.yaml"}, cleanBefore = true, cleanAfter = true)
    void testDeleteHotelPhoto() {
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/api/v1/hotels/photos/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DataSet(value = {"dataset/location/locations.yaml", "dataset/hotel/hotels.yaml", "dataset/photo/photos.yaml"}, cleanBefore = true, cleanAfter = true)
    void testGetHotelPhotos() {
        ResponseEntity<List<PhotoUploadResponse>> response = testRestTemplate.exchange(
                "/api/v1/hotels/1/photos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DataSet(value = {"dataset/location/locations.yaml", "dataset/hotel/hotels.yaml"}, cleanBefore = true, cleanAfter = true)
    void testCreateHotelPhotos() {
        Resource resource = new ClassPathResource("dataset/photo/icon.png");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("photos", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                "/api/v1/hotels/1/photos",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        System.out.println(response.getBody());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DataSet(value = {"dataset/location/locations.yaml", "dataset/hotel/hotels.yaml", "dataset/photo/photos.yaml"}, cleanBefore = true, cleanAfter = true)
    void testDeleteHotelPhoto() {
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/api/v1/hotels/photos/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
