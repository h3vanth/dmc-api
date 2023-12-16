package io.bbw.dmc.event.producer;

import io.bbw.dmc.event.ProductCategoryRemovalEvent;
import io.bbw.dmc.event.ProductCreatedEvent;
import io.bbw.dmc.event.ProductDeletedEvent;
import io.bbw.dmc.event.ProductUpdatedEvent;
import io.bbw.dmc.model.Product;
import org.springframework.beans.BeanUtils;

public class ProductEventProducer {

    public static ProductCreatedEvent produceProductCreatedEvent(Product product) {
        ProductCreatedEvent event = new ProductCreatedEvent();
        BeanUtils.copyProperties(product, event);
        return event;
    }

    public static ProductUpdatedEvent produceProductUpdatedEvent(Product productBeforeUpdate, Product product) {
        ProductUpdatedEvent event = new ProductUpdatedEvent();
        BeanUtils.copyProperties(product, event);
        event.setProductBeforeUpdate(productBeforeUpdate);
        return event;
    }

    // can only get productId with this event
    public static ProductDeletedEvent produceProductDeletedEvent(Product product) {
        ProductDeletedEvent event = new ProductDeletedEvent();
        BeanUtils.copyProperties(product, event);
        return event;
    }

    public static ProductCategoryRemovalEvent produceProductCategoryRemovalEvent(Product productBeforeUpdate, Product product) {
        ProductCategoryRemovalEvent event = new ProductCategoryRemovalEvent();
        BeanUtils.copyProperties(product, event);
        event.setCategoriesBeforeRemoval(productBeforeUpdate.getCategories());
        return event;
    }
}
