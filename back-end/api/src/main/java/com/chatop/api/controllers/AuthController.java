package com.chatop.api.controllers;

import com.chatop.api.dto.*;
import com.chatop.api.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "Gestion de l'inscription et la connexion")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "S'inscrire", description = "Crée un nouvel utilisateur et retourne un token")
    @ApiResponse(responseCode = "200", description = "Inscription réussie")
    @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Se connecter", description = "Récupérer le token de session à partir de l'email et du mot de passe.")
    @ApiResponse(responseCode = "200", description = "Connexion réussie - Retourne un token")
    @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) { //RequestBody car les données sont dans le corps de la requète et non l'URL
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Récupérer l'utilisateur actuel", description = "Vérifie la validité du token et retourne les informations de l’utilisateur identifié.")
    @ApiResponse(responseCode = "200", description = "Utilisateur récupéré avec succès")
    @ApiResponse(responseCode = "401", description = "Token invalide ou utilisateur non connecté")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(Principal principal){
        //On récupère l'utilisateur authentifié grâce au principal (context Spring)
        UserResponse userResponse = authService.getMe(principal.getName());
        return ResponseEntity.ok(userResponse);

    }
}
