package io.bbw.dmc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import io.bbw.dmc.exception.EntityNotFoundException;
import io.bbw.dmc.pojo.Product;
import io.bbw.dmc.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts(String userId) {
        return productRepository.findAllByUserId(userId);
    }

    @Override
    public Product getProductByProductId(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(productId, Product.class));
    }

    @Override
    public void updateProduct(Product product) {
        Optional<Product> prod = productRepository.findById(product.getProductId());
        if (prod.isPresent()) {
            productRepository.save(product);
        } else {
            throw new EntityNotFoundException(product.getProductId(), Product.class);
        }
    }

    @Override
    public void deleteProducts(String productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds.split("-")) {
            products.add(Product.builder().productId(productId).build());
        }
        productRepository.deleteAll(products);
    }

    @Override
    public Product addProduct(Product product, String userId) {
        product.setUserId(userId);
        return productRepository.save(product);
    }
}
