package com.keerthi.incident_platform.incident.dto;

import com.keerthi.incident_platform.incident.IncidentStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateIncidentStatusRequest {

    @NotNull
    private IncidentStatus status;

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }
}