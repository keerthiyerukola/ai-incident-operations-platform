
package com.keerthi.incident_platform.event;

import com.keerthi.incident_platform.event.dto.CreateEventRequest;
import com.keerthi.incident_platform.event.dto.EventResponse;
import com.keerthi.incident_platform.service.MonitoredService;
import com.keerthi.incident_platform.service.ServiceRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final ServiceRepository serviceRepository;

    public EventService(
            EventRepository eventRepository,
            ServiceRepository serviceRepository) {
        this.eventRepository = eventRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public EventResponse ingestEvent(CreateEventRequest request) {

        MonitoredService monitoredService =
                serviceRepository.findById(request.getServiceId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Monitored service not found with id: "
                                        + request.getServiceId()
                        ));

        ServiceEvent event = new ServiceEvent(
                monitoredService,
                request.getEventType().trim(),
                request.getSeverity(),
                request.getMessage().trim(),
                request.getSource().trim(),
                request.getOccurredAt()
        );

        ServiceEvent savedEvent = eventRepository.save(event);

        return EventResponse.from(savedEvent);
    }

    public List<EventResponse> getEvents() {
        return eventRepository.findAll()
                .stream()
                .map(EventResponse::from)
                .toList();
    }
}
