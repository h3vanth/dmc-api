package io.bbw.dmc.event;

import io.bbw.dmc.model.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductUpdatedEvent extends ProductEvent {
    private Product productBeforeUpdate;
}
