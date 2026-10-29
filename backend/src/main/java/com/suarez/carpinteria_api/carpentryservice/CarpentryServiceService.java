package com.suarez.carpinteria_api.carpentryservice;

import com.suarez.carpinteria_api.carpentryservice.dto.CarpentryServiceRequest;
import com.suarez.carpinteria_api.carpentryservice.dto.CarpentryServiceResponse;
import com.suarez.carpinteria_api.common.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarpentryServiceService {

    private final CarpentryServiceRepository serviceRepository;

    public CarpentryServiceService(CarpentryServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional(readOnly = true)
    public List<CarpentryServiceResponse> findAll() {
        return serviceRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CarpentryServiceResponse findById(Long id) {
        return toResponse(getServiceOrThrow(id));
    }

    @Transactional
    public CarpentryServiceResponse create(CarpentryServiceRequest request) {
        CarpentryService service = new CarpentryService(request.name(), request.description(), request.price());
        return toResponse(serviceRepository.save(service));
    }

    @Transactional
    public CarpentryServiceResponse update(Long id, CarpentryServiceRequest request) {
        CarpentryService service = getServiceOrThrow(id);
        service.update(request.name(), request.description(), request.price());
        return toResponse(service);
    }

    @Transactional
    public void delete(Long id) {
        serviceRepository.delete(getServiceOrThrow(id));
    }

    private CarpentryService getServiceOrThrow(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carpentry service", id));
    }

    private CarpentryServiceResponse toResponse(CarpentryService service) {
        return new CarpentryServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice()
        );
    }
}