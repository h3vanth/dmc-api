package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.Event;
import io.bbw.dmc.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEventListener implements Listener {
    @Override
    public void handleEvent(Event event) {
        if (event instanceof UserCreatedEvent userCreatedEvent) {
            log.info("UserCreatedEvent {}", userCreatedEvent);
        }
    }
}
