package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Détails publics d'un utilisateur")
public class UserResponse {

    @Schema(description = "Identifiant de l'utilisateur", example = "1")
    private Integer id;

    @Schema(description = "Nom complet", example = "Owner Name")
    private String name;

    @Schema(description = "Adresse email", example = "test@test.com")
    private String email;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @Schema(description = "Date de création", example = "2022/02/02")
    private LocalDateTime created_at;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @Schema(description = "Date de dernière mise à jour", example = "2022/08/02")
    private LocalDateTime updated_at;
}
