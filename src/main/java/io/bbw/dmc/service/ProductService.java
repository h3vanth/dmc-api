package io.bbw.dmc.service;

import java.util.List;

import io.bbw.dmc.pojo.Product;

public interface ProductService {
    List<Product> getProducts(String userId);

    Product getProductByProductId(String productId);

    void updateProduct(Product product);

    Product addProduct(Product product, String userId);

    void deleteProducts(String productIds);
}
