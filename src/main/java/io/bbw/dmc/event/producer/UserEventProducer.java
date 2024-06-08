package io.bbw.dmc.event.producer;

import io.bbw.dmc.event.ProductCategoryCreatedEvent;
import io.bbw.dmc.event.UserCreatedEvent;
import io.bbw.dmc.model.User;
import org.springframework.beans.BeanUtils;

public class UserEventProducer {
    public static UserCreatedEvent produceUserCreatedEvent(User user) {
        UserCreatedEvent event = new UserCreatedEvent();
        BeanUtils.copyProperties(user, event);
        return event;
    }

    public static ProductCategoryCreatedEvent produceProductCategoryCreatedEvent(User user) {
        ProductCategoryCreatedEvent event = new ProductCategoryCreatedEvent();
        BeanUtils.copyProperties(user, event);
        return event;
    }
}
