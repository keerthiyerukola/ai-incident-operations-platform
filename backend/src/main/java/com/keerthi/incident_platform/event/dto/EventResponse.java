
package com.keerthi.incident_platform.event.dto;

import com.keerthi.incident_platform.event.ServiceEvent;
import com.keerthi.incident_platform.incident.IncidentSeverity;

import java.time.LocalDateTime;

public class EventResponse {

    private final Long id;
    private final Long serviceId;
    private final String serviceName;
    private final String eventType;
    private final IncidentSeverity severity;
    private final String message;
    private final String source;
    private final LocalDateTime occurredAt;
    private final LocalDateTime receivedAt;
    private final Long incidentId;

    public EventResponse(
            Long id,
            Long serviceId,
            String serviceName,
            String eventType,
            IncidentSeverity severity,
            String message,
            String source,
            LocalDateTime occurredAt,
            LocalDateTime receivedAt,
            Long incidentId) {

        this.id = id;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.eventType = eventType;
        this.severity = severity;
        this.message = message;
        this.source = source;
        this.occurredAt = occurredAt;
        this.receivedAt = receivedAt;
        this.incidentId = incidentId;
    }

    public static EventResponse from(ServiceEvent event) {
        return new EventResponse(
                event.getId(),
                event.getMonitoredService().getId(),
                event.getMonitoredService().getName(),
                event.getEventType(),
                event.getSeverity(),
                event.getMessage(),
                event.getSource(),
                event.getOccurredAt(),
                event.getReceivedAt(),
                event.getIncident() == null
                        ? null
                        : event.getIncident().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getEventType() {
        return eventType;
    }

    public IncidentSeverity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public String getSource() {
        return source;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public Long getIncidentId() {
        return incidentId;
    }
}
