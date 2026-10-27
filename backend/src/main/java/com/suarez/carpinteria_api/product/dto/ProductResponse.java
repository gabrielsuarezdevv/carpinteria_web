package com.suarez.carpinteria_api.product.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Integer stock,
        BigDecimal price) {}
