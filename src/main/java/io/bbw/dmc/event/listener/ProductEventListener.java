package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductEventListener implements Listener {
    @Override
    public void handleEvent(Event event) {
        if (event instanceof ProductCreatedEvent productCreatedEvent) {
            log.info("ProductCreatedEvent {}", productCreatedEvent);
        } else if (event instanceof ProductUpdatedEvent productUpdatedEvent) {
            log.info("ProductUpdatedEvent {}", productUpdatedEvent);
        } else if (event instanceof ProductDeletedEvent productDeletedEvent) {
            log.info("ProductDeletedEvent {}", productDeletedEvent);
        } else if (event instanceof ProductCategoryRemovalEvent productCategoryRemovalEvent) {
            log.info("ProductCategoryRemovalEvent {}", productCategoryRemovalEvent);
        }
    }
}
