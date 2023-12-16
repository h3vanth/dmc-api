package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductEventListener implements Listener {
    private final static Logger logger = LoggerFactory.getLogger(ProductEventListener.class);

    @Override
    public void handleEvent(Event event) {
        if (event instanceof ProductCreatedEvent productCreatedEvent) {
            logger.info("ProductCreatedEvent {}", productCreatedEvent);
        } else if (event instanceof ProductUpdatedEvent productUpdatedEvent) {
            logger.info("ProductUpdatedEvent {}", productUpdatedEvent);
        } else if (event instanceof ProductDeletedEvent productDeletedEvent) {
            logger.info("ProductDeletedEvent {}", productDeletedEvent);
        } else if (event instanceof ProductCategoryRemovalEvent productCategoryRemovalEvent) {
            logger.info("ProductCategoryRemovalEvent {}", productCategoryRemovalEvent);
        }
    }
}
