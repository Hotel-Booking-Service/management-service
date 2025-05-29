package com.hbs.managamentservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Адресное расположение отеля")
public class LocationRequest {

    @Schema(
            description = "Страна",
            example = "Estonia",
            maxLength = 100
    )
    @NotBlank(message = "Country is required")
    @Size(max = 100)
    @Pattern(
            regexp = "^[\\p{L} .'-]+$",
            message = "Only letters, spaces, dots, hyphens and apostrophes are allowed"
    )
    private String country;

    @Schema(
            description = "Город",
            example = "Tallinn",
            maxLength = 100
    )
    @NotBlank(message = "City is required")
    @Size(max = 100)
    @Pattern(
            regexp = "^[\\p{L} .'-]+$",
            message = "Only letters, spaces, dots, hyphens and apostrophes are allowed"
    )
    private String city;

    @Schema(
            description = "Улица",
            example = "Pikk Street",
            maxLength = 100
    )
    @NotBlank(message = "Street is required")
    @Size(max = 100)
    @Pattern(
            regexp = "^[\\p{L} .'-]+$",
            message = "Only letters, spaces, dots, hyphens and apostrophes are allowed"
    )
    private String street;

    @Schema(
            description = "Номер дома",
            example = "12A",
            maxLength = 20
    )
    @NotBlank(message = "Building is required")
    @Size(max = 20)
    @Pattern(
            regexp = "^[\\p{L}\\d\\-/ ]{1,20}$",
            message = "Invalid building format"
    )
    private String building;

    @Schema(
            description = "Почтовый индекс",
            example = "10123",
            maxLength = 20
    )
    @NotBlank(message = "Zip code is required")
    @Size(max = 20)
    @Pattern(
            regexp = "^[\\d\\p{L}\\- ]{3,20}$",
            message = "Invalid zip code format"
    )
    private String zipCode;
}
