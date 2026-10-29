package com.suarez.carpinteria_api.carpentryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CarpentryServiceRequest(
        @NotBlank @Size(max = 150) String name,
        String description,
        @PositiveOrZero BigDecimal price
) {
}