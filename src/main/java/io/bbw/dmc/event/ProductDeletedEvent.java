package io.bbw.dmc.event;

import lombok.ToString;

@ToString(callSuper = true)
public class ProductDeletedEvent extends ProductEvent {
    public static final String TYPE = "ProductDeleted";

    public ProductDeletedEvent() {
        super(TYPE);
    }
}
