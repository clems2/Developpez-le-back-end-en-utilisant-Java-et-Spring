package com.chatop.api.controllers;

import com.chatop.api.dto.MessageRequest;
import com.chatop.api.dto.MessageResponse;
import com.chatop.api.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody MessageRequest request) {
        System.out.println("********* Nouveau message reçu :  " + request.getMessage() + " *********");
        messageService.saveMessage(request);
        return ResponseEntity.ok(new MessageResponse("Message send with success"));
    }
}
