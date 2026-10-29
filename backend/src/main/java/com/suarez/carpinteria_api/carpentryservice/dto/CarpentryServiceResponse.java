package com.suarez.carpinteria_api.carpentryservice.dto;

import java.math.BigDecimal;

public record CarpentryServiceResponse(
        Long id,
        String name,
        String description,
        BigDecimal price) {}
