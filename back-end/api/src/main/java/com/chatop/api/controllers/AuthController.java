package com.chatop.api.controllers;

import com.chatop.api.dto.AuthResponse;
import com.chatop.api.dto.LoginRequest;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.dto.UserResponse;
import com.chatop.api.models.User;
import com.chatop.api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        System.out.println("********* register *********");
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) { //RequestBody car les données sont dans le corps de la requète et non l'URL
        System.out.println("********* login *********");
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(){
        System.out.println("********* getMe *********");
        //On récupère l'utilisateur authentifié grâce aux filtres appliqués
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponse userResponse = authService.getMe(email);
        return ResponseEntity.ok(userResponse);

    }
}
