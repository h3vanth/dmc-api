package io.bbw.dmc.event.listener;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeadEventListener {
    @Subscribe
    public void handleEvent(DeadEvent event) {
        log.info("DeadEvent {}", event);
    }
}
