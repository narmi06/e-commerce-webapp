package com.example.lkclothing_backend.controller;

import com.example.lkclothing_backend.entity.Product;
import com.example.lkclothing_backend.entity.ProductVariant;
import com.example.lkclothing_backend.repository.ProductRepository;
import com.example.lkclothing_backend.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductVariantRepository productVariantRepository) {
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
    }

    // 1. GET: See all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. POST: Add a new product (Now includes productCode)
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 3. POST: Add a variant to a specific product
    // URL Example: http://localhost:8080/api/products/1/variants
    @PostMapping("/{productId}/variants")
    public ProductVariant addVariantToProduct(@PathVariable Long productId, @RequestBody ProductVariant variant) {
        // First, find the product in the database using the ID
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            // Link the variant to the product
            variant.setProduct(product);
            // Save the variant to the database
            return productVariantRepository.save(variant);
        } else {
            throw new RuntimeException("Product not found with id " + productId);
        }
    }
}