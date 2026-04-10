package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Requête de création d'une location (Format Form-Data)")
public class RentalCreateRequest {
    @NotBlank
    @Size(max = 255)
    @Schema(description = "Nom de la location", example = "Superbe Villa avec piscine")
    private String name;

    @NotNull
    @Min(1)
    @Schema(description = "Surface en m2", example = "120")
    private Integer surface;

    @NotNull
    @Min(1)
    @Schema(description = "Prix de la location", example = "1500")
    private Integer price;

    @NotBlank
    @Size(max = 2000)
    @Schema(description = "Description détaillée", example = "Maison lumineuse située au calme...")
    private String description;

    @NotNull(message = "La photo est obligatoire pour créer une location")
    @Schema(description = "Fichier image de la location")
    private MultipartFile picture;
}
