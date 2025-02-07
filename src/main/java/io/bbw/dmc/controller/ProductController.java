package io.bbw.dmc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;

import io.bbw.dmc.model.Product;
import io.bbw.dmc.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/v1/products")
    public ResponseEntity<List<Product>> getProducts(Principal principal) {
        return new ResponseEntity<>(productService.getProducts(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/v1/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        return new ResponseEntity<>(productService.getProduct(productId),
                HttpStatus.OK);
    }

    @PutMapping("/v1/products")
    public ResponseEntity<HttpStatus> updateProduct(@Valid @RequestBody Product product) {
        productService.updateProduct(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/v1/products")
    public ResponseEntity<Product> addProduct(@RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("productName") String productName,
            @RequestParam("price") BigDecimal price,
            @RequestParam("availableQuantity") int availableQuantity,
            @RequestParam("description") String description,
            @RequestParam("isAvailable") boolean isAvailable,
            @RequestParam("categories") String[] categories,
            Principal principal) {
        String userId = principal.getName();
        Product product = Product.builder().productName(productName).price(price).availableQuantity(availableQuantity)
                .description(description).isAvailable(isAvailable).categories(categories).userId(userId).build();
        productService.addProduct(product, image, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Check length restrictions
    @DeleteMapping("/v1/products/{productIds}")
    public ResponseEntity<Product> deleteProducts(@PathVariable String productIds, Principal principal) {
        productService.deleteProducts(productIds, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/v1/products/{productId}/categories/{category}")
    public ResponseEntity<?> removeCategory(@PathVariable String productId, @PathVariable String category) {
        productService.removeCategory(productId, category);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
