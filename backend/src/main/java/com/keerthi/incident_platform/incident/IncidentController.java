package com.keerthi.incident_platform.incident;

import com.keerthi.incident_platform.incident.dto.CreateIncidentRequest;
import com.keerthi.incident_platform.incident.dto.IncidentResponse;
import com.keerthi.incident_platform.incident.dto.UpdateIncidentRequest;
import com.keerthi.incident_platform.incident.dto.UpdateIncidentStatusRequest;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public IncidentResponse createIncident(
            @Valid @RequestBody CreateIncidentRequest request) {

        return incidentService.createIncident(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'VIEWER')")
    public List<IncidentResponse> getIncidents(
            @RequestParam(required = false) Long serviceId,
            @RequestParam(required = false) IncidentStatus status,
            @RequestParam(required = false) IncidentSeverity severity) {

        return incidentService.getIncidents(serviceId, status, severity);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'VIEWER')")
    public IncidentResponse getIncidentById(@PathVariable Long id) {
        return incidentService.getIncidentById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public IncidentResponse updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIncidentRequest request) {

        return incidentService.updateIncident(id, request);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public IncidentResponse updateIncidentStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIncidentStatusRequest request) {

        return incidentService.updateIncidentStatus(
                id, request.getStatus());
    }
}