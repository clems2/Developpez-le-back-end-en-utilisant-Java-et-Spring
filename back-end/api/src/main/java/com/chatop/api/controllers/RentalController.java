package com.chatop.api.controllers;

import com.chatop.api.dto.*;
import com.chatop.api.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Rentals", description = "Gestion des annonces de location")
public class RentalController {
    private final RentalService rentalService;
    @Operation(summary = "Récupérer toutes les locations", description = "Retourne la liste complète des biens immobiliers disponibles.")
    @ApiResponse(responseCode = "200", description = "Liste des locations récupérée")
    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
    @GetMapping
    public ResponseEntity<RentalsResponse> getAll() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @Operation(summary = "Récupérer une location", description = "Retourne le détail d'une location spécifique par son ID.")
    @ApiResponse(responseCode = "200", description = "Détail de la location récupéré")
    @ApiResponse(responseCode = "401", description = "Location non trouvée ou non authentifié")    @GetMapping("/{id}")
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

    @Operation(summary = "Créer une location", description = "Permet d'ajouter une nouvelle annonce avec une image.")
    @ApiResponse(responseCode = "200", description = "Location crée")
    @ApiResponse(responseCode = "401", description = "Propriétaire non trouvé ou utilisateur non authentifié")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> create(
            @Valid @ModelAttribute RentalCreateRequest request, //Spring se charge du mapping du formulaire
            Principal principal
    ) {
        // On appelle le service pour une location et on récupère le mail dans le Principal
        rentalService.createOneRental(request, principal.getName());

        return ResponseEntity.ok(new MessageResponse("Rental created !"));
    }

    @Operation(summary = "Modifier une location", description = "Met à jour les informations d'une location (nécessite d'être le propriétaire).")
    @ApiResponse(responseCode = "200", description = "Location mise à jour !")
    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié ou autorisé ou location non trouvée")
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
