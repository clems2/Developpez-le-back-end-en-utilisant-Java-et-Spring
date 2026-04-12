package com.chatop.api.services;

import com.chatop.api.dto.*;
import com.chatop.api.exceptions.UnauthorizedException;
import com.chatop.api.mappers.RentalMapper;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final RentalMapper rentalMapper;

    public RentalsResponse getAllRentals() {
        List<RentalDto> rentals = rentalRepository.findAll().stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());

        log.info("Rentals founded");
        return new RentalsResponse(rentals);
    }

    public RentalDto getRentalById(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new UnauthorizedException("Rental not found"));
        log.info("Rentals founded with id : {}",id);
        return rentalMapper.toDto(rental);
    }


    public void createOneRental(RentalCreateRequest request, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UnauthorizedException("Owner not found"));
        log.info("owner founded");

        Rental rental = rentalMapper.toEntity(request);

        String pictureUrl = uploadPicture(request.getPicture());
        rental.setPicture(pictureUrl);
        rental.setOwner(owner);
        rentalRepository.save(rental);
        log.info("rental created");
    }

    private String uploadPicture(MultipartFile picture) {
        if (picture == null || picture.isEmpty()) {
            return "";
        }
        try {
            String fileName = System.currentTimeMillis() + "_" + picture.getOriginalFilename();

            Path path = Paths.get("src/main/resources/static/images/" + fileName);

            Files.copy(picture.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
           return baseUrl + "/images/" + fileName;
        } catch (IOException e) {
            log.error("Erreur lors de l'upload de l'image : {}", e.getMessage());
            throw new RuntimeException("Impossible de sauvegarder l'image, erreur interne lors de l'upload", e);
        }
    }

    public void updateRental(Integer id, RentalUpdateRequest request, String currentUserEmail) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new UnauthorizedException("Rental not found"));
        log.info("rental founded");

        if (!rental.getOwner().getEmail().equals(currentUserEmail)) {
            log.error("Owner and current user does not match");
            throw new UnauthorizedException("You are not authorized to update this rental");
        }

        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        rentalRepository.save(rental);
    }
}
