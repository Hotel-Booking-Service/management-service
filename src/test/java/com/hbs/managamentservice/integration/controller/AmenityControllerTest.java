package com.hbs.managamentservice.integration.controller;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.hbs.managamentservice.dto.response.AmenityResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@DBUnit(caseSensitiveTableNames = true, schema = "public")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AmenityControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DataSet(value = "dataset/amenity/amenities.yaml", cleanBefore = true)
    void testGetAmenities() {
        ResponseEntity<List<AmenityResponse>> response = testRestTemplate.exchange(
                "/api/v1/amenities",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<AmenityResponse> amenities = response.getBody();
        assertNotNull(amenities);
        assertThat(amenities).hasSize(1);
        assertThat(amenities.getFirst().id()).isEqualTo(1);
        assertThat(amenities.getFirst().name()).isEqualTo("Test Amenity");
    }

    @Test
    @DataSet(value = "dataset/amenity/amenities.yaml", cleanBefore = true)
    void testGetAmenityById() {
        ResponseEntity<AmenityResponse> response = testRestTemplate.getForEntity(
                "/api/v1/amenities/1",
                AmenityResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        AmenityResponse amenityResponse = response.getBody();
        assertNotNull(amenityResponse);
        assertThat(amenityResponse.id()).isEqualTo(1);
        assertThat(amenityResponse.name()).isEqualTo("Test Amenity");
    }

    @Test
    void testCreateAmenity() {
        ByteArrayResource fileAsResource = new ByteArrayResource("test".getBytes()) {
            @Override
            public String getFilename() {
                return "test.jpg";
            }
        };

        HttpHeaders filePartHeaders = new HttpHeaders();
        filePartHeaders.setContentDispositionFormData("icon", "test.jpg");
        filePartHeaders.setContentType(MediaType.IMAGE_JPEG);
        HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(fileAsResource, filePartHeaders);

        HttpHeaders stringPartHeaders = new HttpHeaders();
        stringPartHeaders.setContentDispositionFormData("name", null);
        HttpEntity<String> namePart = new HttpEntity<>("Test Amenity", stringPartHeaders);

        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        multipartRequest.add("name", namePart);
        multipartRequest.add("icon", filePart);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multipartRequest, headers);

        ResponseEntity<AmenityResponse> response = testRestTemplate.exchange(
                "/api/v1/amenities",
                HttpMethod.POST,
                requestEntity,
                AmenityResponse.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Amenity", response.getBody().name());
    }

    @Test
    @DataSet(value = "dataset/amenity/amenities.yaml", cleanBefore = true)
    void testUpdateAmenity() {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "Test Amenity");

        HttpHeaders headers = createFileHeaders();

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<AmenityResponse> response = testRestTemplate.exchange(
                "/api/v1/amenities/1",
                HttpMethod.PATCH,
                requestEntity,
                AmenityResponse.class
        );

        assertNotNull(response.getBody());
        assertEquals("Test Amenity", response.getBody().name());
    }

    @Test
    @DataSet(value = "dataset/amenity/amenities.yaml", cleanBefore = true)
    void testDeleteAmenity() {
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/api/v1/amenities/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private HttpHeaders createFileHeaders() {
        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentDispositionFormData("icon", "test.jpg");
        fileHeaders.setContentType(MediaType.parseMediaType("image/jpeg"));
        return fileHeaders;
    }
}
