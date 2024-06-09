package io.bbw.dmc.service;

import io.bbw.dmc.event.handler.EventHandler;
import io.bbw.dmc.event.producer.ProductEventProducer;
import io.bbw.dmc.exception.EntityNotFoundException;
import io.bbw.dmc.model.Product;
import io.bbw.dmc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
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
    }

    @Override
    public void deleteProducts(String productIds, String userId) {
        productRepository.deleteAll(
                Stream.of(productIds.split("-"))
                        .map(productId -> {
                            var product = Product.builder().productId(productId).userId(userId).build();
                            eventHandler.emitEvent(ProductEventProducer.produceProductDeletedEvent(product));
                            return product;
                        })
                        .collect(Collectors.toList()));
    }

    @Override
    public void addProduct(Product product, MultipartFile image, String userId) {
        var savedProduct = productRepository.save(product);
        eventHandler.emitEvent(ProductEventProducer.produceProductCreatedEvent(savedProduct));
        if (image == null) {
            return;
        }
        var fileExtension = image.getContentType().replace("image/", ".");
        savedProduct.setImageUrl(
                new StringBuilder(System.getenv("HOST")).append("/images/").append(savedProduct.getProductId())
                        .append(fileExtension).toString());
        var absolutePath = new File("src/main/resources/static/images").getAbsolutePath();
        var fileName = new StringBuilder(absolutePath).append("/").append(savedProduct.getProductId())
                .append(fileExtension).toString();
        try (OutputStream os = new FileOutputStream(fileName, false)) {
            os.write(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed!");
        }
        productRepository.save(savedProduct);
    }

    @Override
    public void removeCategory(String productId, String category) {
        productRepository.findById(productId)
                .ifPresentOrElse(
                        product -> {
                            String[] categoriesBeforeUpdate = product.getCategories();
                            product.setCategories(
                                    Arrays.stream(product.getCategories())
                                            .filter(cat -> !cat.equals(category))
                                            .toArray(String[]::new));
                            productRepository.save(product);
                            eventHandler.emitEvent(ProductEventProducer.produceProductCategoryRemovedEvent(categoriesBeforeUpdate, productRepository.save(product)));
                        },
                        () -> {
                            throw new EntityNotFoundException(productId, Product.class);
                        }
                );
    }
}
