package com.suarez.carpinteria_api.carpentryservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.suarez.carpinteria_api.carpentryservice.dto.CarpentryServiceRequest;
import com.suarez.carpinteria_api.carpentryservice.dto.CarpentryServiceResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/services")
public class CarpentryServiceController {

    private final CarpentryServiceService serviceService;

    public CarpentryServiceController(CarpentryServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<CarpentryServiceResponse> findAll() {
        return serviceService.findAll();
    }

    @GetMapping("/{id}")
    public CarpentryServiceResponse findById(@PathVariable Long id) {
        return serviceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarpentryServiceResponse create(@Valid @RequestBody CarpentryServiceRequest request) {
        return serviceService.create(request);
    }

    @PutMapping("/{id}")
    public CarpentryServiceResponse update(@PathVariable Long id, @Valid @RequestBody CarpentryServiceRequest request) {
        return serviceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        serviceService.delete(id);
    }
}
