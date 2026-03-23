package com.chatop.api.controllers;

import com.chatop.api.dto.UserResponse;
import com.chatop.api.models.User;
import com.chatop.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(){
        //On récupère l'utilisateur authentifié grâce aux filtres appliqués
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponse userResponse = authService.getMe(email);
        return ResponseEntity.ok(userResponse);

    }
}
