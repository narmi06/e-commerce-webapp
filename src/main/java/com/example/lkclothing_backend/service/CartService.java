package com.example.lkclothing_backend.service;

import com.example.lkclothing_backend.dto.AddToCartRequest;
import com.example.lkclothing_backend.entity.Cart;
import com.example.lkclothing_backend.entity.CartItem;
import com.example.lkclothing_backend.entity.ProductVariant;
import com.example.lkclothing_backend.entity.User;
import com.example.lkclothing_backend.repository.CartRepository;
import com.example.lkclothing_backend.repository.ProductVariantRepository;
import com.example.lkclothing_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository variantRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductVariantRepository variantRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.variantRepository = variantRepository;
    }

    // Get the user's cart (or create a new empty one if they don't have one yet)
    public Cart getCartForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart(user);
            return cartRepository.save(newCart);
        });
    }

    // Add an item to the cart
    public Cart addItemToCart(String email, AddToCartRequest request) {
        // 1. Get the cart
        Cart cart = getCartForUser(email);

        // 2. Find the exact product variant (e.g., Red Medium Shirt)
        ProductVariant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new RuntimeException("Product variant not found"));

        // 3. INVENTORY CHECK! Do we have enough in stock?
        if (variant.getStockQuantity() < request.getQuantity()) {
            throw new RuntimeException("Not enough stock available! Only " + variant.getStockQuantity() + " left.");
        }

        // 4. Check if this item is ALREADY in the cart. If so, just increase the quantity.
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductVariant().getId().equals(variant.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            // Double check stock again
            if (variant.getStockQuantity() < (item.getQuantity() + request.getQuantity())) {
                throw new RuntimeException("Cannot add more. Not enough stock available.");
            }
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            // 5. If it's a new item, create it and add it to the cart
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductVariant(variant);
            newItem.setQuantity(request.getQuantity());
            cart.getItems().add(newItem);
        }

        // 6. Save and return the updated cart
        return cartRepository.save(cart);
    }
}