package com.example.lkclothing_backend.repository;

import com.example.lkclothing_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // We add a custom method here because we will need to find users by their email when they try to log in!
    Optional<User> findByEmail(String email);

}