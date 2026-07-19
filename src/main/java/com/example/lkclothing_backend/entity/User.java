package com.example.lkclothing_backend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
// We name the table "users" (plural) because "user" is a reserved system word in many SQL databases
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // Emails must be unique - no two users can have the same email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // We need this for the WhatsApp/SMS integrations later!
    @Column(unique = true)
    private String phoneNumber;

    // Stores the role as a String (e.g., "CUSTOMER" or "ADMIN") instead of a number
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Automatically records the exact date and time the user created their account
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // --- Constructors ---
    public User() {}

    public User(String firstName, String lastName, String email, String password, String phoneNumber, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}