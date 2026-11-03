package com.suarez.carpinteria_api.servicerequest;

import com.suarez.carpinteria_api.carpentryservice.CarpentryService;
import com.suarez.carpinteria_api.carpentryservice.CarpentryServiceRepository;
import com.suarez.carpinteria_api.common.ResourceNotFoundException;
import com.suarez.carpinteria_api.servicerequest.dto.ServiceRequestRequest;
import com.suarez.carpinteria_api.servicerequest.dto.ServiceRequestResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final CarpentryServiceRepository carpentryServiceRepository;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository, CarpentryServiceRepository carpentryServiceRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.carpentryServiceRepository = carpentryServiceRepository;
    }

    @Transactional(readOnly = true)
    public List<ServiceRequestResponse> findAll() {
        return serviceRequestRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ServiceRequestResponse findById(Long id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Service request", id));
        return toResponse(serviceRequest);
    }

    @Transactional
    public ServiceRequestResponse create(ServiceRequestRequest request) {
        CarpentryService service = carpentryServiceRepository.findById(request.serviceId())
            .orElseThrow(() -> new ResourceNotFoundException("Carpentry service", request.serviceId()));

        ServiceRequest serviceRequest = new ServiceRequest(
            service,
            request.name(),
            request.email(),
            request.phone(),
            request.message()
        );

        return toResponse(serviceRequestRepository.save(serviceRequest));
    }

    private ServiceRequestResponse toResponse(ServiceRequest serviceRequest) {
        CarpentryService service = serviceRequest.getService();
        return new ServiceRequestResponse(
            serviceRequest.getId(),
            serviceRequest.getName(),
            serviceRequest.getEmail(),
            serviceRequest.getPhone(),
            serviceRequest.getMessage(),
            serviceRequest.getStatus(),
            serviceRequest.getCreatedAt(),
            service.getId(),
            service.getName()
        );
    }
}
