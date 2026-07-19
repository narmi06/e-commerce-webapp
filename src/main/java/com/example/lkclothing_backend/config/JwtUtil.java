package com.example.lkclothing_backend.config;

import com.example.lkclothing_backend.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // The badge is valid for 24 hours (in milliseconds)
    private static final long EXPIRATION_TIME = 86400000;

    public String generateToken(User user) {
        return Jwts.builder()
                // The subject is usually the user's unique identifier (email)
                .setSubject(user.getEmail())
                // We can add extra info (claims) to the badge, like their role
                .claim("role", user.getRole().name())
                .claim("firstName", user.getFirstName())
                // Issued right now
                .setIssuedAt(new Date())
                // Expires in 24 hours
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                // Sign it with our secret key so hackers can't forge fake badges
                .signWith(SECRET_KEY)
                .compact();
    }

    // --- NEW METHODS FOR THE FILTER TO READ THE TOKEN ---

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token) {
        try {
            // This line will crash if the token is fake or expired, which is how we know it's invalid!
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}