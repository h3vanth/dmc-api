package io.bbw.dmc.pojo;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private String productId;

    private String userId;

    @NotBlank(message = "Product name is required")
    private String productName;

    @Min(value = 0, message = "Price cannot be negative")
    private BigDecimal price;

    @JsonProperty(value = "isAvailable")
    private boolean isAvailable;

    private String description;
    private int availableQuantity;
    private String imageUrl;
}
