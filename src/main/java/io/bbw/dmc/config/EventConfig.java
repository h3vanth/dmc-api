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
    public EventBus eventBus(UserEventListener userEventListener, ProductEventListener productEventListener, OrderEventListener orderEventListener, DeadEventListener deadEventListener) {
        EventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
        eventBus.register(userEventListener);
        eventBus.register(productEventListener);
        eventBus.register(orderEventListener);
        eventBus.register(deadEventListener);
        return eventBus;
    }
}
