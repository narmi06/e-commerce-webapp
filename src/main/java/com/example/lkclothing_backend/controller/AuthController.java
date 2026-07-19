package com.example.lkclothing_backend.controller;

import com.example.lkclothing_backend.config.JwtUtil;
import com.example.lkclothing_backend.dto.AuthResponse;
import com.example.lkclothing_backend.dto.RegisterRequest;
import com.example.lkclothing_backend.dto.LoginRequest;
import com.example.lkclothing_backend.entity.User;
import com.example.lkclothing_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User newUser = userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully! ID: " + newUser.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // 1. Verify the user's email and password
            User loggedInUser = userService.loginUser(request);

            // 2. Generate the ID Badge (JWT)
            String token = jwtUtil.generateToken(loggedInUser);

            // 3. Send the badge and user info back to the React frontend
            AuthResponse response = new AuthResponse(token, loggedInUser.getFirstName(), loggedInUser.getRole().name());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}