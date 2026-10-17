
package com.keerthi.incident_platform.service;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "monitored_services",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_monitored_service_name",
            columnNames = "name"
        )
    }
)
public class MonitoredService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceEnvironment environment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceStatus status = ServiceStatus.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected MonitoredService() {
        // Required by JPA
    }

    public MonitoredService(
            String name,
            String description,
            ServiceEnvironment environment
    ) {
        this.name = name;
        this.description = description;
        this.environment = environment;
        this.status = ServiceStatus.ACTIVE;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(ServiceEnvironment environment) {
        this.environment = environment;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
