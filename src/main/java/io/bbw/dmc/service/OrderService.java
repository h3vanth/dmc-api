package io.bbw.dmc.service;

import java.util.List;

import io.bbw.dmc.pojo.Order;

public interface OrderService {
    List<Order> placeOrder(Order[] orders, String userId);

    List<Order> getOrders(String userId);
}
