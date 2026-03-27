package com.chatop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RentalsResponse {
    private List<RentalDto> rentals;
}
