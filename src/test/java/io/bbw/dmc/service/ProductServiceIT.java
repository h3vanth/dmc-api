package io.bbw.dmc.service;

import io.bbw.dmc.exception.EntityNotFoundException;
import io.bbw.dmc.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;

@SpringBootTest
public class ProductServiceIT {
    private static final Product PRODUCT_1 = new Product("123", "1", "Biryani", new BigDecimal(150), true, "", 0, "", null);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductService productService;

    @AfterEach
    public void afterEach() {
        mongoTemplate.remove(new Query(), Product.class);
    }

    @Test
    void addProductTest() {
        productService.addProduct(PRODUCT_1, null, "1");

        Assertions.assertNotNull(mongoTemplate.findById("123", Product.class));
    }

    @Test
    void getProductTest() {
        productService.addProduct(PRODUCT_1, null, "1");

        Assertions.assertNotNull(productService.getProduct("123"));
    }

    @Test
    void getProductsTest() {
        productService.addProduct(PRODUCT_1, null, "1");

        Assertions.assertEquals(1, productService.getProducts("1").size());
    }

    @Test
    void getProduct_InvalidProductIdTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            productService.getProduct("234");
        });
    }
}
