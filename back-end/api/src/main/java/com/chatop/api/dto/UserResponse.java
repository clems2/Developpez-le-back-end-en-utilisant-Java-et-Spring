package com.chatop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor //Le @Builder en a besoin pour sa compilation
@NoArgsConstructor //Indispensable pour Hibernate et Jackson car ils créent un objet vide pour le remplir
@Builder
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private LocalDateTime created_at; // Format attendu par le front
    private LocalDateTime updated_at;
}
