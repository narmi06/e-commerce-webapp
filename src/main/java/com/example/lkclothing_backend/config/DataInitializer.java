package com.example.lkclothing_backend.config;

import com.example.lkclothing_backend.entity.Role;
import com.example.lkclothing_backend.entity.User;
import com.example.lkclothing_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "mr.kolin0319@gmail.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setFirstName("Kolin");
            admin.setLastName("Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("kolin123"));
            admin.setRole(Role.ADMIN);
            admin.setPhoneNumber("0000000000");
            
            userRepository.save(admin);
            System.out.println("=============================================");
            System.out.println("Default admin user created: " + adminEmail);
            System.out.println("=============================================");
        }
    }
}
