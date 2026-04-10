package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Requête de connexion")
public class LoginRequest {
    @NotBlank
    @Email
    @Schema(description = "Adresse email de l'utilisateur", example = "test@test.com")
    private String email;

    @NotBlank
    @Schema(description = "Mot de passe de l'utilisateur", example = "Password123!")
    private String password;
}
