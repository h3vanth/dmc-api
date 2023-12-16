package io.bbw.dmc.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductCategoryRemovalEvent extends ProductEvent {
    private String[] categoriesBeforeRemoval;
}
