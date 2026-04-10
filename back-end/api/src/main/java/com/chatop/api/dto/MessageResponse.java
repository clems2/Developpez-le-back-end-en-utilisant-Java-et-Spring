package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Réponse standard contenant un message de succès")
public class MessageResponse {
    @Schema(description = "Message de retour", example = "Rental created !")
    private String message;
}
