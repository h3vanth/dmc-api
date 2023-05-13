package io.bbw.dmc.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import io.bbw.dmc.pojo.Order;
import io.bbw.dmc.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MessageService messageService;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Override
    public List<Order> placeOrder(Order[] orders, String userId) {
        for (Order order : orders) {
            int orderQuantity = 0;
            String orderId = order.getOrderId();
            if (orderId != null) {
                orderQuantity = orderRepository.findById(orderId).get().getQuantity();
            }
            var product = productService.getProduct(order.getProductId());
            var availableQuantity = product.getAvailableQuantity() - (order.getQuantity() - orderQuantity);
            product.setAvailableQuantity(availableQuantity);
            if (availableQuantity == 0) {
                product.setAvailable(false);
            }
            productService.updateProduct(product);
            order.setUserId(userId);
        }
        orderRepository.saveAll(Arrays.asList(orders));
        messageService.sendMessage(userId);
        // Maybe sessionId can be sent in headers
        return orderRepository.findAllBySessionIdAndUserId(orders[0].getSessionId(), userId);
    }

    @Override
    public List<Order> getOrders(String userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
