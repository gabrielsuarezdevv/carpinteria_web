package com.suarez.carpinteria_api.servicerequest;

import com.suarez.carpinteria_api.servicerequest.dto.ServiceRequestRequest;
import com.suarez.carpinteria_api.servicerequest.dto.ServiceRequestResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping
    public List<ServiceRequestResponse> findAll() {
        return serviceRequestService.findAll();
    }

    @GetMapping("/{id}")
    public ServiceRequestResponse findById(@PathVariable Long id) {
        return serviceRequestService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceRequestResponse create(@Valid @RequestBody ServiceRequestRequest request) {
        return serviceRequestService.create(request);
    }
}
