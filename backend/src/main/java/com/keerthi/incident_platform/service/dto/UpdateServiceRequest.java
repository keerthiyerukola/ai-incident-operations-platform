
package com.keerthi.incident_platform.service.dto;

import com.keerthi.incident_platform.service.ServiceEnvironment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateServiceRequest {

    @NotBlank(message = "Service name is required")
    @Size(max = 100, message = "Service name cannot exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Environment is required")
    private ServiceEnvironment environment;

    public UpdateServiceRequest() {
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
}
