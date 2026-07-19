package com.example.lkclothing_backend.repository;

import com.example.lkclothing_backend.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    // We will use this later to find specific variants by their SKU barcode
}