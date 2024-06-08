package io.bbw.dmc.service;

import java.util.Arrays;
import java.util.List;

import io.bbw.dmc.event.handler.EventHandler;
import io.bbw.dmc.event.producer.OrderEventProducer;
import io.bbw.dmc.event.producer.ProductEventProducer;
import io.bbw.dmc.model.OrderStatus;
import io.bbw.dmc.model.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import io.bbw.dmc.model.Order;
import io.bbw.dmc.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final EventHandler eventHandler;

    @Override
    public List<Order> placeOrder(Order[] orders, String userId) {
        for (Order order : orders) {
            int orderQuantity = 0;
            String orderId = order.getOrderId();
            if (orderId != null) {
                orderQuantity = orderRepository.findById(orderId).get().getQuantity();
            }
            var product = productService.getProduct(order.getProductId());
            var productBeforeUpdate = new Product();
            BeanUtils.copyProperties(product, productBeforeUpdate);
            var availableQuantity = product.getAvailableQuantity() - (order.getQuantity() - orderQuantity);
            product.setAvailableQuantity(availableQuantity);
            if (availableQuantity == 0) {
                product.setAvailable(false);
            }
            eventHandler.emitEvent(ProductEventProducer.produceProductUpdatedEvent(productBeforeUpdate, product));
            productService.updateProduct(product);
            order.setUserId(userId);
            if (order.getOrderStatus() == null) {
                order.setOrderStatus(OrderStatus.PLACED);
            }
        }
        orderRepository.saveAll(Arrays.asList(orders));
        eventHandler.emitEvents(OrderEventProducer.produceOrderPlacedEvents(orders));
        // Maybe sessionId can be sent in headers
        return orderRepository.findAllBySessionIdAndUserId(orders[0].getSessionId(), userId);
    }

    @Override
    public List<Order> getOrders(String userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
