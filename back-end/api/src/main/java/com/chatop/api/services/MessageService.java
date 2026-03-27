package com.chatop.api.services;

import com.chatop.api.dto.MessageRequest;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;
import com.chatop.api.repositories.MessageRepository;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public void saveMessage(MessageRequest request) {
        // Récupération de l'expéditeur
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Récupération de la location concernée
        Rental rental = rentalRepository.findById(request.getRental_id())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        // Construction de l'entité Message
        Message messageEntity = Message.builder()
                .message(request.getMessage())
                .user(user)
                .rental(rental)
                .build();

        // On sauvegarde en base le message
        messageRepository.save(messageEntity);
    }
}
