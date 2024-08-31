package io.bbw.dmc.event.producer;

import io.bbw.dmc.event.OrderPlacedEvent;
import io.bbw.dmc.model.Order;
import io.formulate.common.event.Event;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderEventProducer {
    public static OrderPlacedEvent produceOrderPlacedEvent(Order order) {
        OrderPlacedEvent event = new OrderPlacedEvent();
        BeanUtils.copyProperties(order, event);
        return event;
    }

    public static List<Event> produceOrderPlacedEvents(Order... orders) {
        List<Event> events = new ArrayList<>();
        Arrays.stream(orders).forEach(order -> {
            events.add(produceOrderPlacedEvent(order));
        });
        return events;
    }
}
