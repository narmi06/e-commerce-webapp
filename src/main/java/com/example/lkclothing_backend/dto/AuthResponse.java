package com.example.lkclothing_backend.dto;

// We use this object to send a structured JSON response back to the React frontend
public class AuthResponse {
    private String token;
    private String firstName;
    private String role;

    public AuthResponse(String token, String firstName, String role) {
        this.token = token;
        this.firstName = firstName;
        this.role = role;
    }

    // Getters
    public String getToken() { return token; }
    public String getFirstName() { return firstName; }
    public String getRole() { return role; }
}