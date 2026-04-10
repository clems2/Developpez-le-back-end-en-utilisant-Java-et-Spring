package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Requête d'inscription")
public class RegisterRequest {
    @NotBlank
    @Size(max = 255)
    @Schema(description = "Nom complet", example = "Jean Dupont")
    private String name;

    @NotBlank
    @Email
    @Size(max = 255)
    @Schema(description = "Adresse email", example = "jean.dupont@test.com")
    private String email;

    @NotBlank
    @Size(min = 6, max = 255)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Mot de passe", example = "SecurePass!89")
    private String password;
}
