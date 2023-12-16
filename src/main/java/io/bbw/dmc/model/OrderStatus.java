package io.bbw.dmc.model;

import java.util.stream.Stream;

public enum OrderStatus {
    // P -> PS ==> Once the bill is settled.
    PLACED("P"),
    PLACED_AND_SETTLED("PS");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public static OrderStatus from(String status) {
        return Stream.of(OrderStatus.values())
                .filter(orderStatus -> orderStatus.toString().equalsIgnoreCase(status))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return status;
    }
}
