package io.bbw.dmc.event.handler;

import com.google.common.eventbus.EventBus;
import io.bbw.dmc.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventHandler {
    private final EventBus eventBus;

    public void emitEvent(Event event) {
        log.info("Emitting event {}", event);
        eventBus.post(event);
    }

    public void emitEvents(List<Event> events) {
        events.forEach(event -> {
            emitEvent(event);
        });
    }
}
