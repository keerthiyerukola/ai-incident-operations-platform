
package com.keerthi.incident_platform.service;

import com.keerthi.incident_platform.service.dto.CreateServiceRequest;
import com.keerthi.incident_platform.service.dto.ServiceResponse;
import com.keerthi.incident_platform.service.dto.UpdateServiceRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceResponse createService(
            @Valid @RequestBody CreateServiceRequest request
    ) {
        return serviceService.createService(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'VIEWER')")
    public List<ServiceResponse> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'VIEWER')")
    public ServiceResponse getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceResponse updateService(
            @PathVariable Long id,
            @Valid @RequestBody UpdateServiceRequest request
    ) {
        return serviceService.updateService(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceResponse deactivateService(@PathVariable Long id) {
        return serviceService.deactivateService(id);
    }
}
