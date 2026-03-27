package com.chatop.api.controllers;

import com.chatop.api.dto.*;
import com.chatop.api.services.RentalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<RentalsResponse> getAll() {
        System.out.println("getAllRentals");
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getOne(@PathVariable Integer id) {
        System.out.println("getOneRental");
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    //Mockoon Version
    /*@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> create(
            @RequestParam("rentals") String rentalsJson, // La liste arrive sous forme de texte JSON
            @RequestParam("pictures") List<MultipartFile> pictures // Les fichiers binaires à part
    ) throws JsonProcessingException {

        // On utilise ObjectMapper pour transformer le texte JSON en liste d'objets
        ObjectMapper objectMapper = new ObjectMapper();
        RentalListRequestDto requestWrapper = objectMapper.readValue(rentalsJson, RentalListRequestDto.class);

        //Récupération du user connecté
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // On envoie tout au service pour traitement
        rentalService.createRentalsFromList(requestWrapper.getRentals(), pictures, email);
        return ResponseEntity.ok(new MessageResponse("Rental created !"));
    }*/

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> create(
            @RequestParam("name") String name, //envoyer via formulaire (formData) donc passer en clair et @RequestParam
            @RequestParam("surface") BigDecimal surface,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture // Le fichier envoyé par le front
    ) {
        System.out.println("createRental");

        // 1. Récupérer l'email de l'utilisateur connecté via le Token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Appeler le service pour UNE SEULE location
        rentalService.createOneRental(name, surface, price, description, picture, email);

        return ResponseEntity.ok(new MessageResponse("Rental created !"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> update(
            @PathVariable("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("surface") BigDecimal surface,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description
    ) {
        System.out.println("putRental");
        rentalService.updateRental(id, name, surface, price, description);
        return ResponseEntity.ok(new MessageResponse("Rental updated !"));
    }
}
