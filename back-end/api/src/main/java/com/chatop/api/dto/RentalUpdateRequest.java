package com.chatop.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalUpdateRequest {
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @Min(1)
    private Integer surface;

    @NotNull
    @Min(1)
    private Integer price;

    @NotBlank
    @Size(max = 2000)
    private String description;
}
