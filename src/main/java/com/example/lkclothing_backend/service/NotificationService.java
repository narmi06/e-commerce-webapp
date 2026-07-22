package com.example.lkclothing_backend.service;

import com.example.lkclothing_backend.entity.Order;

public interface NotificationService {
    void sendOrderConfirmation(Order order);
    void sendShippingUpdate(Order order);
}
