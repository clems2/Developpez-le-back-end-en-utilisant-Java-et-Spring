package com.chatop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RentalListRequestDto {
    private List<RentalRequestDto> rentals;
}
