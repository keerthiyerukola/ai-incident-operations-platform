
package com.keerthi.incident_platform.event;

import com.keerthi.incident_platform.incident.Incident;
import com.keerthi.incident_platform.incident.IncidentSeverity;
import com.keerthi.incident_platform.service.MonitoredService;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_events")
public class ServiceEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private MonitoredService monitoredService;

    @Column(nullable = false, length = 100)
    private String eventType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IncidentSeverity severity;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false, length = 100)
    private String source;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime receivedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id")
    private Incident incident;

    protected ServiceEvent() {
    }

    public ServiceEvent(
            MonitoredService monitoredService,
            String eventType,
            IncidentSeverity severity,
            String message,
            String source,
            LocalDateTime occurredAt) {

        this.monitoredService = monitoredService;
        this.eventType = eventType;
        this.severity = severity;
        this.message = message;
        this.source = source;
        this.occurredAt = occurredAt;
    }

    @PrePersist
    protected void onCreate() {
        receivedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public MonitoredService getMonitoredService() {
        return monitoredService;
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

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }
}
