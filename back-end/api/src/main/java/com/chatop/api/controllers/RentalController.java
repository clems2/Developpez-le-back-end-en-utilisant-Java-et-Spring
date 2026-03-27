package com.chatop.api.controllers;

import com.chatop.api.dto.RentalDto;
import com.chatop.api.dto.RentalsResponse;
import com.chatop.api.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<RentalsResponse> getAll() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }
}
