package com.example.lkclothing_backend.service;

import com.example.lkclothing_backend.dto.CheckoutDto;
import com.example.lkclothing_backend.entity.*;
import com.example.lkclothing_backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(UserRepository userRepository, CartRepository cartRepository,
                        OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        ProductVariantRepository productVariantRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productVariantRepository = productVariantRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Long checkout(Principal principal, CheckoutDto request) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.ZERO); // Will update later
        
        Order savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cart.getItems()) {
            ProductVariant variant = cartItem.getProductVariant();
            if (variant.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for SKU: " + variant.getSku());
            }

            variant.setStockQuantity(variant.getStockQuantity() - cartItem.getQuantity());
            productVariantRepository.save(variant);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProductVariant(variant);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(variant.getPrice());
            
            orderItemRepository.save(orderItem);

            BigDecimal itemTotal = variant.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
            
            orderItems.add(orderItem);
        }

        savedOrder.setTotalAmount(totalAmount);
        orderRepository.save(savedOrder);

        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();

        return savedOrder.getId();
    }
}
