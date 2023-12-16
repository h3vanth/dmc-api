package io.bbw.dmc.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    private String orderId;
    private String userId;
    private String productName;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private String sessionId;
}
