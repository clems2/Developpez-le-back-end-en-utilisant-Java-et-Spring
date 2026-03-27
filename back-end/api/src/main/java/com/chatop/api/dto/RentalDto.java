package com.chatop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
    private Integer id;
    private String name;
    private Double surface;
    private Double price;
    private String picture;
    private String description;
    private Integer owner_id; // On renvoie juste l'ID, pas tout l'objet User
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
