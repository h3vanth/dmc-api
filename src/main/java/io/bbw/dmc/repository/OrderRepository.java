package io.bbw.dmc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.bbw.dmc.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findAllBySessionIdAndUserId(String sessionId, String userId);

    List<Order> findAllByUserId(String userId);
}
