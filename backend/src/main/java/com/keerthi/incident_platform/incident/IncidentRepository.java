package com.keerthi.incident_platform.incident;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    @Query("""
        SELECT i FROM Incident i
        WHERE (:serviceId IS NULL OR i.monitoredService.id = :serviceId)
          AND (:status IS NULL OR i.status = :status)
          AND (:severity IS NULL OR i.severity = :severity)
        ORDER BY i.createdAt DESC
        """)
    List<Incident> findByFilters(
            @Param("serviceId") Long serviceId,
            @Param("status") IncidentStatus status,
            @Param("severity") IncidentSeverity severity);
}