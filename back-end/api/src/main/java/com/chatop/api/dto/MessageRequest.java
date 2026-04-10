package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requête pour l'envoi d'un message")
public class MessageRequest {
    @NotBlank
    @Size(max = 2000)
    @Schema(description = "Contenu du message", example = "Bonjour, cette location est-elle toujours disponible ?")
    private String message;

    @NotNull
    @Schema(description = "ID de l'utilisateur qui envoie le message", example = "1")
    private Integer user_id;

    @NotNull
    @Schema(description = "ID de la location concernée", example = "1")
    private Integer rental_id;
}
