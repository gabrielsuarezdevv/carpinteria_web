package com.suarez.carpinteria_api.servicerequest.dto;

import java.time.LocalDateTime;

import com.suarez.carpinteria_api.servicerequest.ServiceRequestStatus;

public record ServiceRequestResponse(
    Long id,
    String name,
    String email,
    String phone,
    String message,
    ServiceRequestStatus status,
    LocalDateTime createdAt,
    Long serviceId,
    String serviceName
) {
}
