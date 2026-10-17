
package com.keerthi.incident_platform.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<ServiceEvent, Long> {
}
