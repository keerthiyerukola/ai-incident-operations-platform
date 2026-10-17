
package com.keerthi.incident_platform.service.dto;

import com.keerthi.incident_platform.service.MonitoredService;
import com.keerthi.incident_platform.service.ServiceEnvironment;
import com.keerthi.incident_platform.service.ServiceStatus;

import java.time.LocalDateTime;

public class ServiceResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final ServiceEnvironment environment;
    private final ServiceStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ServiceResponse(
            Long id,
            String name,
            String description,
            ServiceEnvironment environment,
            ServiceStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.environment = environment;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ServiceResponse from(MonitoredService service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getEnvironment(),
                service.getStatus(),
                service.getCreatedAt(),
                service.getUpdatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ServiceEnvironment getEnvironment() {
        return environment;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
