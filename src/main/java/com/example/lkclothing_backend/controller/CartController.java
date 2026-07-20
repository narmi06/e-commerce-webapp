package com.example.lkclothing_backend.controller;

import com.example.lkclothing_backend.dto.AddToCartRequest;
import com.example.lkclothing_backend.entity.Cart;
import com.example.lkclothing_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // GET /api/cart -> Returns the current user's cart
    @GetMapping
    public ResponseEntity<Cart> getMyCart(Principal principal) {
        Cart cart = cartService.getCartForUser(principal.getName());
        return ResponseEntity.ok(cart);
    }

    // POST /api/cart/add -> Adds an item to the cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(Principal principal, @RequestBody AddToCartRequest request) {
        try {
            Cart updatedCart = cartService.addItemToCart(principal.getName(), request);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/cart/update/{itemId}?quantity=X -> Updates the quantity of an item
    @PutMapping("/update/{itemId}")
    public ResponseEntity<?> updateCartItem(Principal principal, @PathVariable Long itemId, @RequestParam Integer quantity) {
        try {
            Cart updatedCart = cartService.updateItemQuantity(principal.getName(), itemId, quantity);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/cart/remove/{itemId} -> Removes an item from the cart
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeCartItem(Principal principal, @PathVariable Long itemId) {
        try {
            Cart updatedCart = cartService.removeItemFromCart(principal.getName(), itemId);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}