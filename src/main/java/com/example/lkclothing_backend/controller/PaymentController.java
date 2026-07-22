package com.example.lkclothing_backend.controller;

import com.example.lkclothing_backend.entity.Order;
import com.example.lkclothing_backend.entity.OrderStatus;
import com.example.lkclothing_backend.repository.OrderRepository;
import com.example.lkclothing_backend.service.NotificationService;
import com.example.lkclothing_backend.service.PaymentGatewayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentGatewayService paymentGatewayService;
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    public PaymentController(PaymentGatewayService paymentGatewayService,
                             OrderRepository orderRepository,
                             NotificationService notificationService) {
        this.paymentGatewayService = paymentGatewayService;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    @PostMapping("/notify")
    public ResponseEntity<String> notifyWebhook(@RequestParam Map<String, String> payload) {
        String orderIdStr = payload.get("order_id");
        String amount = payload.get("payhere_amount");
        String currency = payload.get("payhere_currency");
        String statusCode = payload.get("status_code");
        String md5sig = payload.get("md5sig");

        if (orderIdStr == null || md5sig == null) {
            return ResponseEntity.badRequest().body("Missing required parameters");
        }

        boolean isValid = paymentGatewayService.verifyWebhookPayload(orderIdStr, amount, currency, statusCode, md5sig);

        if (!isValid) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        if ("2".equals(statusCode)) { // 2 = success in PayHere
            Long orderId = Long.parseLong(orderIdStr);
            Optional<Order> orderOpt = orderRepository.findById(orderId);

            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                order.setStatus(OrderStatus.PAID);
                orderRepository.save(order);

                // Trigger notifications
                notificationService.sendOrderConfirmation(order);
                return ResponseEntity.ok("Success");
            } else {
                return ResponseEntity.badRequest().body("Order not found");
            }
        }

        return ResponseEntity.ok("Received but not successful status");
    }
}
