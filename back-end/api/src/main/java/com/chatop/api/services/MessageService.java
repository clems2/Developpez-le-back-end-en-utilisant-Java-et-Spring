package com.chatop.api.services;

import com.chatop.api.dto.MessageRequest;
import com.chatop.api.exceptions.BadRequestException;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.mappers.MessageMapper;
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
    private final MessageMapper messageMapper;

    public void saveMessage(MessageRequest request) {
        // Récupération de l'expéditeur
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new BadRequestException("User not found with id: "));

        // Récupération de la location concernée
        Rental rental = rentalRepository.findById(request.getRental_id())
                .orElseThrow(() -> new BadRequestException("Rental not found"));

        // Construction de l'entité Message
        Message messageEntity = messageMapper.toEntity(request);

        //On ajoute les champs calculés
        messageEntity.setUser(user);
        messageEntity.setRental(rental);

        // On sauvegarde en base le message
        messageRepository.save(messageEntity);
    }
}
