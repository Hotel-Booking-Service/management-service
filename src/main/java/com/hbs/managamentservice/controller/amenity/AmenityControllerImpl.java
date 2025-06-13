package com.hbs.managamentservice.controller.amenity;

import com.hbs.managamentservice.dto.request.CreateAmenityRequest;
import com.hbs.managamentservice.dto.request.UpdateAmenityRequest;
import com.hbs.managamentservice.dto.response.AmenityResponse;
import com.hbs.managamentservice.service.amenity.AmenityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/amenities")
public class AmenityControllerImpl implements AmenityController {

    private final AmenityService amenityService;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AmenityResponse> getAmenities() {
        return amenityService.getAmenities();
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AmenityResponse getAmenity(@PathVariable Long id) {
        return amenityService.getAmenity(id);
    }

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AmenityResponse createAmenity(@ModelAttribute @Valid CreateAmenityRequest request) {
        return amenityService.createAmenity(request);
    }

    @Override
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AmenityResponse updateAmenity(@PathVariable Long id, @ModelAttribute @Valid UpdateAmenityRequest request) {
        return amenityService.updateAmenity(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
    }
}
