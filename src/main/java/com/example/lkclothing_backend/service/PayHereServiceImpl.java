package com.example.lkclothing_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

@Service
public class PayHereServiceImpl implements PaymentGatewayService {

    @Value("${payhere.merchant.id:test_merchant_id}")
    private String merchantId;

    @Value("${payhere.merchant.secret:test_merchant_secret}")
    private String merchantSecret;

    @Override
    public String generatePaymentHash(String orderId, String amount, String currency) {
        String hashedSecret = getMd5(merchantSecret).toUpperCase();
        String hashString = merchantId + orderId + amount + currency + hashedSecret;
        return getMd5(hashString).toUpperCase();
    }

    @Override
    public boolean verifyWebhookPayload(String orderId, String amount, String currency, String statusCode, String md5sig) {
        // "statusCode" is usually 2 for success in PayHere, but we just verify signature here
        String generatedSig = generatePaymentHash(orderId, amount, currency);
        return generatedSig.equalsIgnoreCase(md5sig);
    }

    private String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
