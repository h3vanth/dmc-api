package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.Event;
import io.bbw.dmc.event.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserEventListener implements Listener {
    private final static Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    @Override
    public void handleEvent(Event event) {
        if (event instanceof UserCreatedEvent userCreatedEvent) {
            logger.info("UserCreatedEvent {}", userCreatedEvent);
        }
    }
}
