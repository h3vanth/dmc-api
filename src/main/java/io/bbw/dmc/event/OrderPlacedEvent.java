package io.bbw.dmc.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class OrderPlacedEvent extends Event {
    public static final String TYPE = "OrderPlaced";

    private String orderId;
    private String productName;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private String sessionId;

    public OrderPlacedEvent() {
        super(TYPE);
    }
}
