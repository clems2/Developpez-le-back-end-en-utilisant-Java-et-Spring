package com.chatop.api.controllers;

import com.chatop.api.dto.*;
import com.chatop.api.services.RentalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Validated //Valide les annotations sur les paramètres directement
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<RentalsResponse> getAll() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getOne(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    //Mockoon Version
    /*@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> create(
            @RequestParam("rentals") String rentalsJson, // La liste arrive sous forme de texte JSON
            @RequestParam("pictures") List<MultipartFile> pictures, // Les fichiers binaires à part
            Principal principal
    ) throws JsonProcessingException {

        // On utilise ObjectMapper pour transformer le texte JSON en liste d'objets
        ObjectMapper objectMapper = new ObjectMapper();
        RentalListRequestDto requestWrapper = objectMapper.readValue(rentalsJson, RentalListRequestDto.class);

        //Récupération du user connecté
        String email = principal.getName();

        // On envoie tout au service pour traitement
        rentalService.createRentalsFromList(requestWrapper.getRentals(), pictures, email);
        return ResponseEntity.ok(new MessageResponse("Rental created !"));
    }*/

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> create(
            @Valid @ModelAttribute RentalCreateRequest request, //Spring se charge du mapping du formulaire
            Principal principal
    ) {
        // On appelle le service pour une location et on récupère le mail dans le Principal
        rentalService.createOneRental(request, principal.getName());

        return ResponseEntity.ok(new MessageResponse("Rental created !"));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> update(
            @PathVariable("id") @Min(1) Integer id,
            @Valid @ModelAttribute  RentalUpdateRequest request, //Spring se charge du mapping du formulaire
            Principal principal // Injecté automatiquement
    ) {
        rentalService.updateRental(id, request, principal.getName());
        return ResponseEntity.ok(new MessageResponse("Rental updated !"));
    }
}
