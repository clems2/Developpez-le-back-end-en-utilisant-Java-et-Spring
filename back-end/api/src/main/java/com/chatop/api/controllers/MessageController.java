package com.chatop.api.controllers;

import com.chatop.api.dto.MessageRequest;
import com.chatop.api.dto.MessageResponse;
import com.chatop.api.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Envoi de messages")
public class MessageController {
    private final MessageService messageService;

    @Operation(summary = "Envoyer un message à un propriétaire", description = "Permet d'envoyer un message au propriétaire d'une location.")
    @ApiResponse(responseCode = "200", description = "Message envoyé avec succès")
    @ApiResponse(responseCode = "400", description = "Requête invalide (ID utilisateur ou location inexistant)")
    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid  @RequestBody MessageRequest request) {
        messageService.saveMessage(request);
        return ResponseEntity.ok(new MessageResponse("Message send with success"));
    }
}
