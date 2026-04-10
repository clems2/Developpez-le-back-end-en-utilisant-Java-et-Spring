package com.chatop.api.services;

import com.chatop.api.dto.*;
import com.chatop.api.exceptions.UnauthorizedException;
import com.chatop.api.mappers.RentalMapper;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final RentalMapper rentalMapper; //On injecte le mapper (to entity, to dto)

    public RentalsResponse getAllRentals() {
        List<RentalDto> rentals = rentalRepository.findAll().stream()
                .map(rentalMapper::toDto) // Utilisation du mapper : ultra propre !
                .collect(Collectors.toList());

        log.info("Rentals founded");
        return new RentalsResponse(rentals);
    }

    public RentalDto getRentalById(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new UnauthorizedException("Rental not found"));
        log.info("Rentals founded with id : {}",id);
        return rentalMapper.toDto(rental); //plus besoin de la methode utilitaire
    }


    public void createOneRental(RentalCreateRequest request, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UnauthorizedException("Owner not found")); //On récupère l'objet User complet à partir de l'email du token
        log.info("owner founded");
        // Simulation pour le moment
        String pictureUrl = "";
        //Création de l'entity mappé et gestion des champs complexes ensuite
        Rental rental = rentalMapper.toEntity(request);
        //Gestion de l'upload du fichier
        if (request.getPicture() != null && !request.getPicture().isEmpty()) {
            try {
                // Création d'un nom unique : timestamp + nom original
                String fileName = System.currentTimeMillis() + "_" + request.getPicture().getOriginalFilename();

                // Chemin absolu vers mon dossier static
                Path path = Paths.get("src/main/resources/static/images/" + fileName);

                // Copie physique du fichier
                Files.copy(request.getPicture().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                // Construction de l'URL finale pour la BDD
                //On va le récupérer de façon dynamique avec le contexte Spring pour éviter des conflits sur la config
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                pictureUrl = baseUrl + "/images/" + fileName;
                //TODO .requestMatchers("/images/**").permitAll() dans le filterChain voir si c'est la bonne solution car en termes de sécurité c'est pas terrible
            } catch (IOException e) {
                throw new RuntimeException("Impossible de sauvegarder l'image, erreur interne lors de l'upload", e);
            }
        }
        rental.setPicture(pictureUrl);
        rental.setOwner(owner);
        rentalRepository.save(rental);
        log.info("rental created");
    }

    //PUT
    public void updateRental(Integer id, RentalUpdateRequest request, String currentUserEmail) {
        // On cherche la location existante
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new UnauthorizedException("Rental not found"));
        log.info("rental founded");

        //On vérifie que l'owner et le user courant sont les mêmes
        if (!rental.getOwner().getEmail().equals(currentUserEmail)) {
            log.error("Owner and current user does not match");
            throw new UnauthorizedException("You are not authorized to update this rental");
        }

        // On met à jour les champs autorisés
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());

        // Note : On ne touche PAS à rental.getPicture(), //TODO voir s'il y a des sécurités ou annotation pour eviter qu'on y accède

        // Sauvegarde (Update en SQL)
        rentalRepository.save(rental);
    }
}
