package io.bbw.dmc.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductCategoryRemovedEvent extends ProductEvent {
    public static final String TYPE = "ProductCategoryRemoved";

    private String[] categoriesBeforeUpdate;

    public ProductCategoryRemovedEvent() {
        super(TYPE);
    }
}
