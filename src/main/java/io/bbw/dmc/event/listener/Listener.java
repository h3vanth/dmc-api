package io.bbw.dmc.event.listener;

import com.google.common.eventbus.Subscribe;
import io.bbw.dmc.event.Event;

public interface Listener {
    @Subscribe
    void handleEvent(Event event);
}
