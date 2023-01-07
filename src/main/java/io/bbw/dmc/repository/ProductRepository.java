package io.bbw.dmc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.bbw.dmc.pojo.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findAllByUserId(String userId);
}