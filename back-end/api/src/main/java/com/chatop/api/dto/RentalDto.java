package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
    private Integer id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;
    private Integer owner_id; // On renvoie juste l'ID, pas tout l'objet User
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime created_at;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime updated_at;
}
