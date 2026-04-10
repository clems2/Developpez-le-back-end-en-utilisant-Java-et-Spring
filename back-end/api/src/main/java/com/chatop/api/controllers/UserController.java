package com.chatop.api.controllers;

import com.chatop.api.dto.UserResponse;
import com.chatop.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Gestion des informations des utilisateurs")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Récupérer un utilisateur par son ID")
    @ApiResponse(responseCode = "200", description = "Informations utilisateur récupérées")
    @ApiResponse(responseCode = "401", description = "Utilisateur non trouvé ou non authentifié")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") @Min(1) Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
