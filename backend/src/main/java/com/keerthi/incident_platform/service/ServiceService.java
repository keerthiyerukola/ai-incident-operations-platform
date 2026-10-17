
package com.keerthi.incident_platform.service;

import com.keerthi.incident_platform.service.dto.CreateServiceRequest;
import com.keerthi.incident_platform.service.dto.ServiceResponse;
import com.keerthi.incident_platform.service.dto.UpdateServiceRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public ServiceResponse createService(CreateServiceRequest request) {
        String name = request.getName().trim();

        if (serviceRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A service with this name already exists"
            );
        }

        MonitoredService service = new MonitoredService(
                name,
                normalizeDescription(request.getDescription()),
                request.getEnvironment()
        );

        try {
            MonitoredService savedService = serviceRepository.saveAndFlush(service);
            return ServiceResponse.from(savedService);
        } catch (DataIntegrityViolationException exception) {
            // Also handle a duplicate created concurrently by another request.
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A service with this name already exists"
            );
        }
    }

    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(ServiceResponse::from)
                .toList();
    }

    public ServiceResponse getServiceById(Long id) {
        MonitoredService service = findServiceOrThrow(id);
        return ServiceResponse.from(service);
    }

    @Transactional
    public ServiceResponse updateService(
            Long id,
            UpdateServiceRequest request
    ) {
        MonitoredService service = findServiceOrThrow(id);
        String newName = request.getName().trim();

        boolean nameChanged =
                !service.getName().equalsIgnoreCase(newName);

        if (nameChanged
                && serviceRepository.existsByNameIgnoreCase(newName)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A service with this name already exists"
            );
        }

        service.setName(newName);
        service.setDescription(normalizeDescription(request.getDescription()));
        service.setEnvironment(request.getEnvironment());

        try {
            MonitoredService updatedService = serviceRepository.saveAndFlush(service);
            return ServiceResponse.from(updatedService);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A service with this name already exists"
            );
        }
    }

    @Transactional
    public ServiceResponse deactivateService(Long id) {
        MonitoredService service = findServiceOrThrow(id);

        if (service.getStatus() == ServiceStatus.INACTIVE) {
            return ServiceResponse.from(service);
        }

        service.setStatus(ServiceStatus.INACTIVE);

        MonitoredService updatedService = serviceRepository.save(service);
        return ServiceResponse.from(updatedService);
    }

    private MonitoredService findServiceOrThrow(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Service not found with id: " + id
                ));
    }

    private String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }

        return description.trim();
    }
}
