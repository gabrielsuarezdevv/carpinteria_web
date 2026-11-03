package com.suarez.carpinteria_api.servicerequest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServiceRequestRequest(
    @NotBlank @Size(max = 100) String name,
    @NotBlank @Email @Size(max = 150) String email,
    @Size(max = 30) String phone,
    @NotBlank @Size(max = 2000) String message,
    @NotNull Long serviceId
) { 
}
