package io.bbw.dmc.event.listener;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeadEventListener {
    private final static Logger logger = LoggerFactory.getLogger(DeadEventListener.class);

    @Subscribe
    public void handleEvent(DeadEvent event) {
        logger.info("DeadEvent {}", event);
    }
}
