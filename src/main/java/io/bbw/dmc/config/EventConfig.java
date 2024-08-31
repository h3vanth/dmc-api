package io.bbw.dmc.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import io.bbw.dmc.event.listener.DeadEventListener;
import io.formulate.common.event.EventHandler;
import io.formulate.common.event.Listener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class EventConfig {
    @Bean
    public EventBus eventBus(List<Listener> listeners) {
        EventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
        listeners.forEach(eventBus::register);
        eventBus.register(new DeadEventListener());
        return eventBus;
    }

    @Bean
    public EventHandler eventHandler(EventBus eventBus) {
        return new EventHandler(eventBus);
    }
}
