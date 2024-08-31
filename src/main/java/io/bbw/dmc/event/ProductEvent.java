package io.bbw.dmc.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.formulate.common.event.Event;
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

    // TODO: Can be just 'available'
    @JsonProperty(value = "isAvailable")
    protected boolean isAvailable;
    protected String description;
    protected int availableQuantity;
    protected String imageUrl;
    protected String[] categories;

    public ProductEvent(String type) {
        super(type);
    }
}
