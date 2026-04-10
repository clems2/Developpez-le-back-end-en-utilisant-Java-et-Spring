package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Réponse contenant le token JWT")
public class AuthResponse {
    @Schema(description = "Token Bearer pour l'authentification", example = "eyJhbGciOiJIUzI1NiIsInR5c...")
    private String token;
}
