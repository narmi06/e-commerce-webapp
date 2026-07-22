package com.example.lkclothing_backend.service;

public interface PaymentGatewayService {
    String generatePaymentHash(String orderId, String amount, String currency);
    boolean verifyWebhookPayload(String orderId, String amount, String currency, String statusCode, String md5sig);
}
