package io.bbw.dmc.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductEvent extends Event {
    protected String productId;
    protected String productName;
    protected BigDecimal price;
    protected boolean isAvailable;
    protected String description;
    protected int availableQuantity;
    protected String imageUrl;
    protected String[] categories;
}
