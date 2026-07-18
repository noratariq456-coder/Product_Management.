package com.example.productmanagement.service;

import com.example.productmanagement.entity.Product;
import com.example.productmanagement.exception.ProductNotFoundException;
import com.example.productmanagement.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    // Constructor Injection (only) - NotificationService resolves to
    // EmailNotificationService because it is marked @Primary
    public ProductService(ProductRepository productRepository, NotificationService notificationService) {
        this.productRepository = productRepository;
        this.notificationService = notificationService;
    }

    public List<Product> getAllProducts(Sort sort) {
        return productRepository.findAll(sort);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product createProduct(Product product) {
        Product saved = productRepository.save(product);
        notificationService.sendNotification("New product created: " + saved.getName());
        return saved;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setCategory(updatedProduct.getCategory());
        existing.setPrice(updatedProduct.getPrice());
        existing.setQuantity(updatedProduct.getQuantity());
        existing.setStatus(updatedProduct.getStatus());
        existing.setActive(updatedProduct.isActive());
        existing.setCode(updatedProduct.getCode());
        Product saved = productRepository.save(existing);
        notificationService.sendNotification("Product updated: " + saved.getName());
        return saved;
    }

    public Product patchProduct(Long id, Map<String, Object> updates) {
        Product existing = getProductById(id);

        updates.forEach((field, value) -> {
            switch (field) {
                case "name" -> existing.setName((String) value);
                case "category" -> existing.setCategory((String) value);
                case "price" -> existing.setPrice(new BigDecimal(value.toString()));
                case "quantity" -> existing.setQuantity(Integer.parseInt(value.toString()));
                case "status" -> existing.setStatus((String) value);
                case "active" -> existing.setActive(Boolean.parseBoolean(value.toString()));
                case "code" -> existing.setCode((String) value);
                default -> { /* ignore unknown fields */ }
            }
        });

        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        Product existing = getProductById(id);
        productRepository.delete(existing);
    }
}
