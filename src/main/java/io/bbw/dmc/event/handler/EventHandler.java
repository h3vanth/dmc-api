package io.bbw.dmc.event.handler;

import com.google.common.eventbus.EventBus;
import io.bbw.dmc.event.Event;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);
    private final EventBus eventBus;

    public void emitEvent(Event event) {
        logger.info("Emitting event {}", event);
        eventBus.post(event);
    }

    public void emitEvents(List<Event> events) {
        events.forEach(event -> {
            emitEvent(event);
        });
    }
}
