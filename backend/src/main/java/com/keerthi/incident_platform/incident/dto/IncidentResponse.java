
package com.keerthi.incident_platform.incident.dto;

import com.keerthi.incident_platform.incident.Incident;
import com.keerthi.incident_platform.incident.IncidentSeverity;
import com.keerthi.incident_platform.incident.IncidentStatus;

import java.time.LocalDateTime;

public class IncidentResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final IncidentSeverity severity;
    private final IncidentStatus status;
    private final Long serviceId;
    private final String serviceName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime resolvedAt;

    public IncidentResponse(
            Long id,
            String title,
            String description,
            IncidentSeverity severity,
            IncidentStatus status,
            Long serviceId,
            String serviceName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime resolvedAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resolvedAt = resolvedAt;
    }

    public static IncidentResponse from(Incident incident) {
        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getMonitoredService().getId(),
                incident.getMonitoredService().getName(),
                incident.getCreatedAt(),
                incident.getUpdatedAt(),
                incident.getResolvedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IncidentSeverity getSeverity() {
        return severity;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }
}
