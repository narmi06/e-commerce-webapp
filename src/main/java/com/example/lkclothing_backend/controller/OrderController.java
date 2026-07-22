package com.example.lkclothing_backend.controller;

import com.example.lkclothing_backend.dto.CheckoutDto;
import com.example.lkclothing_backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Long> checkout(Principal principal, @RequestBody CheckoutDto request) {
        Long orderId = orderService.checkout(principal, request);
        return ResponseEntity.ok(orderId);
    }
}
