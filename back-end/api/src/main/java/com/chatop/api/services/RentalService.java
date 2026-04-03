package com.chatop.api.services;

import com.chatop.api.dto.*;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public RentalsResponse getAllRentals() {
        List<RentalDto> rentals = rentalRepository.findAll().stream()
                .map(rental -> RentalDto.builder()
                        .id(rental.getId())
                        .name(rental.getName())
                        .surface(rental.getSurface())
                        .price(rental.getPrice())
                        .picture(rental.getPicture())
                        .description(rental.getDescription())
                        .owner_id(rental.getOwner().getId())
                        .created_at(rental.getCreatedAt())
                        .updated_at(rental.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return new RentalsResponse(rentals);
    }

    public RentalDto getRentalById(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        return mapToDto(rental);
    }

    // Petite méthode utilitaire pour éviter la répétition du mapping
    private RentalDto mapToDto(Rental rental) {
        return RentalDto.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture())
                .description(rental.getDescription())
                .owner_id(rental.getOwner().getId())
                .created_at(rental.getCreatedAt())
                .updated_at(rental.getUpdatedAt())
                .build();
    }

    //MOCKOON VERSION
    public void createRentalsFromList(List<RentalRequestDto> rentalRequests, List<MultipartFile> pictures, String ownerEmail) {
        // 1. On récupère l'objet User complet à partir de l'email du token
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // 2. On boucle sur la liste envoyée par le Front/Mockoon version
        for (int i = 0; i < rentalRequests.size(); i++) {
            RentalRequestDto dto = rentalRequests.get(i);

            // 3. Gestion de l'image (Simulation)
            // Dans un vrai projet, on sauvegarderait pictures.get(i) sur le disque
            // Ici, on met une URL par défaut ou le nom du fichier pour l'exemple
            /*String pictureUrl = (pictures != null && i < pictures.size())
                    ? "assets/images/" + pictures.get(i).getOriginalFilename()
                    : "";*/
            String pictureUrl =  "";
            // 4. Création de l'entité Rental
            Rental rental = Rental.builder()
                    .name(dto.getName())
                    .surface(dto.getSurface())
                    .price(dto.getPrice())
                    .description(dto.getDescription())
                    .picture(pictureUrl)
                    .owner(owner) // On lie la location à l'utilisateur
                    .build();

            // 5. Sauvegarde en BDD
            rentalRepository.save(rental);
        }
    }

    //ONE BY ONE VERSION
    public void createOneRental(RentalCreateRequest request, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found")); //On récupère l'objet User complet à partir de l'email du token

        // Simulation pour le moment
        String pictureUrl = "";

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
                throw new RuntimeException("Impossible de sauvegarder l'image", e);
            }
        }

        Rental rental = Rental.builder()
                .name(request.getName())
                .surface(request.getSurface())
                .price(request.getPrice())
                .description(request.getDescription())
                .picture(pictureUrl)
                .owner(owner)
                .build();

        rentalRepository.save(rental);
    }

    //PUT
    public void updateRental(Integer id, RentalUpdateRequest request, String currentUserEmail) {
        // On cherche la location existante
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        //On vérifie que l'owner et le user courant sont les mêmes
        if (!rental.getOwner().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("You are not authorized to update this rental");
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
