
package com.keerthi.incident_platform.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<MonitoredService, Long> {

    boolean existsByNameIgnoreCase(String name);

    Optional<MonitoredService> findByNameIgnoreCase(String name);
}
