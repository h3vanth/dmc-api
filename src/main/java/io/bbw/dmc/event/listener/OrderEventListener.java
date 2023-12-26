package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.Event;
import io.bbw.dmc.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderEventListener implements Listener {
    @Override
    public void handleEvent(Event event) {
        if (event instanceof OrderPlacedEvent orderPlacedEvent)
            log.info("OrderPlacedEvent {}", orderPlacedEvent);
    }
}
