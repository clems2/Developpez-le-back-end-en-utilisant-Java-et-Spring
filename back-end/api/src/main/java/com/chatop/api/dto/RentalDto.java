package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Détails d'une location")
public class RentalDto {

    @Schema(description = "ID de la location", example = "1")
    private Integer id;

    @Schema(description = "Nom de la location", example = "test house 1")
    private String name;

    @Schema(description = "Surface en m2", example = "432")
    private Integer surface;

    @Schema(description = "Prix", example = "300")
    private Integer price;

    @Schema(description = "URL de l'image", example = "http://localhost:8080/pictures/house.jpg")
    private String picture;

    @Schema(description = "Description", example = "Belle maison...")
    private String description;

    @Schema(description = "ID du propriétaire", example = "1")
    private Integer owner_id;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @Schema(description = "Date de création", example = "2012/12/02")
    private LocalDateTime created_at;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @Schema(description = "Date de mise à jour", example = "2014/12/02")
    private LocalDateTime updated_at;
}
