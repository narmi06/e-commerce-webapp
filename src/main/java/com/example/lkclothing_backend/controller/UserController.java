package com.example.lkclothing_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    // This is a SECURE endpoint. Only users with a valid JWT badge can enter.
    @GetMapping("/profile")
    public ResponseEntity<String> getUserProfile(Principal principal) {
        // "Principal" is a Spring Security object that holds the identity of the currently logged-in user.
        // Because our JWT filter extracted the email and put it in the SecurityContext, we can access it here!
        String userEmail = principal.getName();

        return ResponseEntity.ok("Welcome to your private secure profile! Your email is: " + userEmail);
    }
}