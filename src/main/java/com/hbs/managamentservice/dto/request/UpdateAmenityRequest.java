package com.hbs.managamentservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAmenityRequest {

    @Size(min = 3, max = 50, message = "Name must be between {min} and {max} characters")
    private String name;

    private MultipartFile icon;
}
