package io.bbw.dmc.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import io.bbw.dmc.event.listener.DeadEventListener;
import io.bbw.dmc.event.listener.OrderEventListener;
import io.bbw.dmc.event.listener.ProductEventListener;
import io.bbw.dmc.event.listener.UserEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class EventConfig {

    @Bean
    public EventBus eventBus() {
        EventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(4));
        eventBus.register(new UserEventListener());
        eventBus.register(new ProductEventListener());
        eventBus.register(new OrderEventListener());
        eventBus.register(new DeadEventListener());
        return eventBus;
    }
}
