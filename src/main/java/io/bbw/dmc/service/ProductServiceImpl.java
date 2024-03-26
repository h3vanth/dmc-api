package io.bbw.dmc.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.bbw.dmc.event.handler.EventHandler;
import io.bbw.dmc.event.producer.ProductEventProducer;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import io.bbw.dmc.exception.EntityNotFoundException;
import io.bbw.dmc.model.Product;
import io.bbw.dmc.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ProductRepository productRepository;
    private final EventHandler eventHandler;

    @Override
    public List<Product> getProducts(String userId) {
        return productRepository.findAllByUserId(userId);
    }

    @Override
    public Product getProduct(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(productId, Product.class));
    }

    @Override
    public void updateProduct(Product product) {
        Optional<Product> prod = productRepository.findById(product.getProductId());
        if (prod.isPresent()) {
            productRepository.save(product);
            eventHandler.emitEvent(ProductEventProducer.produceProductUpdatedEvent(prod.get(), product));
        } else {
            throw new EntityNotFoundException(product.getProductId(), Product.class);
        }
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        simpMessagingTemplate.convertAndSend(new StringBuilder().append("/topic/").append(principal.getName()).append("/products").toString(), getProducts(principal.getName()));
    }

    @Override
    public void deleteProducts(String productIds) {
        productRepository.deleteAll(
                Stream.of(productIds.split("-"))
                        .map(productId -> {
                            var product = Product.builder().productId(productId).build();
                            eventHandler.emitEvent(ProductEventProducer.produceProductDeletedEvent(product));
                            return product;
                        })
                        .collect(Collectors.toList()));
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        simpMessagingTemplate.convertAndSend(new StringBuilder().append("/topic/").append(principal.getName()).append("/products").toString(), getProducts(principal.getName()));
    }

    @Override
    public void addProduct(Product product, MultipartFile image, String userId) {
        var savedProduct = productRepository.save(product);
        eventHandler.emitEvent(ProductEventProducer.produceProductCreatedEvent(savedProduct));
        if (image == null) {
            simpMessagingTemplate.convertAndSend(new StringBuilder().append("/topic/").append(userId).append("/products").toString(), getProducts(userId));
            return;
        }
        var fileExtension = image.getContentType().replace("image/", ".");
        savedProduct.setImageUrl(
                new StringBuilder(System.getenv("HOST")).append("/images/").append(savedProduct.getProductId())
                        .append(fileExtension).toString());
        var absolutePath = new File("src/main/resources/static/images").getAbsolutePath();
        var fileName = new StringBuilder(absolutePath).append("/").append(savedProduct.getProductId())
                .append(fileExtension).toString();
        try (OutputStream os = new FileOutputStream(new File(fileName), false)) {
            os.write(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed!");
        }
        productRepository.save(savedProduct);
        simpMessagingTemplate.convertAndSend(new StringBuilder().append("/topic/").append(userId).append("/products").toString(), getProducts(userId));
    }

    @Override
    public void removeCategory(String productId, String category) {
        productRepository.findById(productId)
                .ifPresentOrElse(
                        product -> {
                            var productBeforeUpdate = new Product();
                            BeanUtils.copyProperties(product, productBeforeUpdate);
                            product.setCategories(
                                    Arrays.stream(product.getCategories())
                                            .filter(cat -> !cat.equals(category))
                                            .toArray(String[]::new));
                            productRepository.save(product);
                            eventHandler.emitEvent(ProductEventProducer.produceProductCategoryRemovalEvent(productBeforeUpdate, product));
                            Principal principal = SecurityContextHolder.getContext().getAuthentication();
                            simpMessagingTemplate.convertAndSend(new StringBuilder().append("/topic/").append(principal.getName()).append("/products").toString(), getProducts(principal.getName()));
                        },
                        () -> {
                            throw new EntityNotFoundException(productId, Product.class);
                        }
                );
    }
}
