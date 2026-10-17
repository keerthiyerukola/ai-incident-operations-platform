package com.keerthi.incident_platform.incident;

import com.keerthi.incident_platform.incident.dto.CreateIncidentRequest;
import com.keerthi.incident_platform.incident.dto.IncidentResponse;
import com.keerthi.incident_platform.incident.dto.UpdateIncidentRequest;
import com.keerthi.incident_platform.service.MonitoredService;
import com.keerthi.incident_platform.service.ServiceRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final ServiceRepository serviceRepository;

    public IncidentService(
            IncidentRepository incidentRepository,
            ServiceRepository serviceRepository) {
        this.incidentRepository = incidentRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public IncidentResponse createIncident(CreateIncidentRequest request) {

        MonitoredService monitoredService =
                serviceRepository.findById(request.getServiceId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Monitored service not found with id: "
                                        + request.getServiceId()));

        Incident incident = new Incident(
                request.getTitle().trim(),
                normalizeDescription(request.getDescription()),
                request.getSeverity(),
                monitoredService
        );

        Incident savedIncident = incidentRepository.save(incident);

        return IncidentResponse.from(savedIncident);
    }

    public List<IncidentResponse> getIncidents(
            Long serviceId,
            IncidentStatus status,
            IncidentSeverity severity) {

        return incidentRepository.findByFilters(serviceId, status, severity)
                .stream()
                .map(IncidentResponse::from)
                .toList();
    }

    public IncidentResponse getIncidentById(Long id) {
        return IncidentResponse.from(findIncidentOrThrow(id));
    }

    @Transactional
    public IncidentResponse updateIncident(
            Long id,
            UpdateIncidentRequest request) {

        Incident incident = findIncidentOrThrow(id);

        if (incident.getStatus() == IncidentStatus.RESOLVED
                || incident.getStatus() == IncidentStatus.CLOSED) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot edit a resolved or closed incident");
        }

        incident.setTitle(request.getTitle().trim());
        incident.setDescription(
                normalizeDescription(request.getDescription()));
        incident.setSeverity(request.getSeverity());

        return IncidentResponse.from(incidentRepository.save(incident));
    }

    @Transactional
    public IncidentResponse updateIncidentStatus(
            Long id,
            IncidentStatus newStatus) {

        Incident incident = findIncidentOrThrow(id);
        IncidentStatus currentStatus = incident.getStatus();

        if (!isAllowedTransition(currentStatus, newStatus)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Invalid incident status transition: "
                            + currentStatus + " -> " + newStatus);
        }

        incident.setStatus(newStatus);

        if (newStatus == IncidentStatus.RESOLVED) {
            incident.setResolvedAt(LocalDateTime.now());
        }

        return IncidentResponse.from(incidentRepository.save(incident));
    }

    private boolean isAllowedTransition(
            IncidentStatus current,
            IncidentStatus next) {

        return switch (current) {
            case OPEN ->
                    next == IncidentStatus.ACKNOWLEDGED;

            case ACKNOWLEDGED ->
                    next == IncidentStatus.INVESTIGATING;

            case INVESTIGATING ->
                    next == IncidentStatus.MITIGATED;

            case MITIGATED ->
                    next == IncidentStatus.RESOLVED;

            case RESOLVED ->
                    next == IncidentStatus.CLOSED;

            case CLOSED -> false;
        };
    }

    private Incident findIncidentOrThrow(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Incident not found with id: " + id));
    }

    private String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }

        return description.trim();
    }
}