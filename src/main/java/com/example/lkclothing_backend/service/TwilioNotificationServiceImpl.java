package com.example.lkclothing_backend.service;

import com.example.lkclothing_backend.entity.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TwilioNotificationServiceImpl implements NotificationService {

    @Async
    @Override
    public void sendOrderConfirmation(Order order) {
        // Placeholder for Twilio WhatsApp/SMS API integration
        System.out.println("Sending WhatsApp/SMS order confirmation for Order ID: " + order.getId() + " to " + order.getUser().getPhoneNumber());
    }

    @Async
    @Override
    public void sendShippingUpdate(Order order) {
        // Placeholder for Twilio WhatsApp/SMS API integration
        System.out.println("Sending WhatsApp/SMS shipping update for Order ID: " + order.getId() + " to " + order.getUser().getPhoneNumber());
    }
}
