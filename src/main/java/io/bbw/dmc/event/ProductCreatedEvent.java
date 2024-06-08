package io.bbw.dmc.event;

import lombok.ToString;

@ToString(callSuper = true)
public class ProductCreatedEvent extends ProductEvent {
    public static final String TYPE = "ProductCreated";

    public ProductCreatedEvent() {
        super(TYPE);
    }
}
