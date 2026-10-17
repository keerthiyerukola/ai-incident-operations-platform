
package com.keerthi.incident_platform.event.dto;

import com.keerthi.incident_platform.incident.IncidentSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CreateEventRequest {

    @NotNull(message = "Service ID is required")
    @Positive(message = "Service ID must be positive")
    private Long serviceId;

    @NotBlank(message = "Event type is required")
    @Size(max = 100)
    private String eventType;

    @NotNull(message = "Severity is required")
    private IncidentSeverity severity;

    @NotBlank(message = "Message is required")
    @Size(max = 2000)
    private String message;

    @NotBlank(message = "Source is required")
    @Size(max = 100)
    private String source;

    @NotNull(message = "Occurred-at timestamp is required")
    private LocalDateTime occurredAt;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public IncidentSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(IncidentSeverity severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }
}
