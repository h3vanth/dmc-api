package io.bbw.dmc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.bbw.dmc.model.Product;

public interface ProductService {
    List<Product> getProducts(String userId);

    Product getProduct(String productId);

    void updateProduct(Product product);

    void addProduct(Product product, MultipartFile image, String userId);

    void deleteProducts(String productIds, String userId);

    void removeCategory(String productId, String category);
}
