
package com.keerthi.incident_platform.event;

import com.keerthi.incident_platform.event.dto.CreateEventRequest;
import com.keerthi.incident_platform.event.dto.EventResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public EventResponse ingestEvent(
            @Valid @RequestBody CreateEventRequest request) {

        return eventService.ingestEvent(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'VIEWER')")
    public List<EventResponse> getEvents() {
        return eventService.getEvents();
    }
}
