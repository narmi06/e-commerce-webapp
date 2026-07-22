package com.example.lkclothing_backend.dto;

public class CheckoutDto {
    private String shippingAddress;

    public CheckoutDto() {}

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}
