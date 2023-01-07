package io.bbw.dmc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.bbw.dmc.pojo.Order;
import io.bbw.dmc.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/v1/orders/placeOrder")
    public ResponseEntity<List<Order>> placeOrder(@RequestBody Order[] orders, Principal principal) {
        return new ResponseEntity<>(orderService.placeOrder(orders, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/v1/orders")
    public ResponseEntity<List<Order>> getOrders(Principal principal) {
        return new ResponseEntity<>(orderService.getOrders(principal.getName()), HttpStatus.OK);
    }
}
