package com.example.lkclothing_backend.repository;

import com.example.lkclothing_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository gives us built-in methods like save(), findAll(), findById(), and deleteById() automatically.
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // We can add custom search methods here later if needed (e.g., findByName)
}