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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Requête de mise à jour d'une location (Format Form-Data)")
public class RentalUpdateRequest {
    @NotBlank
    @Size(max = 255)
    @Schema(description = "Nouveau nom", example = "test house 1")
    private String name;

    @NotNull
    @Min(1)
    @Schema(description = "Nouvelle surface", example = "432")
    private Integer surface;

    @NotNull
    @Min(1)
    @Schema(description = "Nouveau prix", example = "300")
    private Integer price;

    @NotBlank
    @Size(max = 2000)
    @Schema(description = "Nouvelle description", example = "Mise à jour de la description...")
    private String description;
}
