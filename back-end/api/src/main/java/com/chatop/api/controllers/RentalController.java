package com.chatop.api.controllers;

import com.chatop.api.dto.*;
import com.chatop.api.services.RentalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Validated //Valide les annotations sur les paramètres directement
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<RentalsResponse> getAll() {
        System.out.println("********* getAllRentals *********");
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getOne(@PathVariable @Min(1) Integer id) {
        System.out.println("********* getOneRental *********");
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
            @Valid @ModelAttribute RentalCreateRequest request //Spring se charge du mapping du formulaire
    ) {
        System.out.println("********* createRental *********");

        // 1. Récupérer l'email de l'utilisateur connecté via le Token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Appeler le service pour UNE SEULE location
        rentalService.createOneRental(request, email);

        return ResponseEntity.ok(new MessageResponse("Rental created !"));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> update(
            @PathVariable("id") Integer id,
            @Valid @ModelAttribute  RentalUpdateRequest request //Spring se charge du mapping du formulaire
    ) {
        System.out.println("********* putRental *********");
        rentalService.updateRental(id, request);
        return ResponseEntity.ok(new MessageResponse("Rental updated !"));
    }
}
