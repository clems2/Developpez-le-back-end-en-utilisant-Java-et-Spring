package com.chatop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDto {
    private String name;
    private Integer surface;
    private Integer price;
    private String description;
    // Note : On ne met pas "picture" ici si on le reçoit
    // séparément en tant que MultipartFile dans le Controller.
}
