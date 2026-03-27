package com.chatop.api.services;

import com.chatop.api.dto.RentalDto;
import com.chatop.api.dto.RentalsResponse;
import com.chatop.api.repositories.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalsResponse getAllRentals() {
        List<RentalDto> rentals = rentalRepository.findAll().stream()
                .map(rental -> RentalDto.builder()
                        .id(rental.getId())
                        .name(rental.getName())
                        .surface(rental.getSurface())
                        .price(rental.getPrice())
                        .picture(rental.getPicture())
                        .description(rental.getDescription())
                        .owner_id(rental.getOwner().getId())
                        .created_at(rental.getCreatedAt())
                        .updated_at(rental.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return new RentalsResponse(rentals);
    }
}
