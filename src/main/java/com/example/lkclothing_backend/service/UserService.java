package com.example.lkclothing_backend.service;

import com.example.lkclothing_backend.dto.RegisterRequest;
import com.example.lkclothing_backend.dto.LoginRequest;
import com.example.lkclothing_backend.entity.Role;
import com.example.lkclothing_backend.entity.User;
import com.example.lkclothing_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest request) {
        // 1. Check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email is already in use!");
        }

        // 2. Create a new User object
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        // Everyone who registers via the website is a CUSTOMER by default
        user.setRole(Role.CUSTOMER);

        // 3. ENCRYPT THE PASSWORD! (Never save it as plain text)
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encryptedPassword);

        // 4. Save to database
        return userRepository.save(user);
    }

    public User loginUser(LoginRequest request) {
        // 1. Find the user by their email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with this email!"));

        // 2. Compare the raw password from the login request with the encrypted one in the database
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        // 3. If it matches, return the user!
        return user;
    }
}