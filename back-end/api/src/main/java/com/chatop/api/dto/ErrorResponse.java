package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Réponse standard en cas d'erreur")
public class ErrorResponse {
    @Schema(description = "Message d'erreur", example = "Invalid credentials")
    private String message;
}
