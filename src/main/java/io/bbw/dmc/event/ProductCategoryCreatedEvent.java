package io.bbw.dmc.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductCategoryCreatedEvent extends Event {
    public static final String TYPE = "ProductCategoryCreated";

    private String[] categories;

    public ProductCategoryCreatedEvent() {
        super(TYPE);
    }
}
