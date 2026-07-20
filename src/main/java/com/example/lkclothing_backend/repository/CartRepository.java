package com.example.lkclothing_backend.repository;

import com.example.lkclothing_backend.entity.Cart;
import com.example.lkclothing_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Allows us to find a user's cart using their User object
    Optional<Cart> findByUser(User user);
}