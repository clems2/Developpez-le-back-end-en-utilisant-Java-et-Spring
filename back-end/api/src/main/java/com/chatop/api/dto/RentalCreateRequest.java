package com.chatop.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalCreateRequest {
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @Min(1)
    private Integer surface;

    @NotNull
    @Min(1)
    private Integer price;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @NotNull(message = "La photo est obligatoire pour créer une location")
    private MultipartFile picture;
}
