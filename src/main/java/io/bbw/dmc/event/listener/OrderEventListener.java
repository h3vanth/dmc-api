package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.Event;
import io.bbw.dmc.event.OrderPlacedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderEventListener implements Listener {
    private final static Logger logger = LoggerFactory.getLogger(OrderEventListener.class);

    @Override
    public void handleEvent(Event event) {
        if (event instanceof OrderPlacedEvent orderPlacedEvent)
            logger.info("OrderPlacedEvent {}", orderPlacedEvent);
    }
}
