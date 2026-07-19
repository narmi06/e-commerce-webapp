package com.example.lkclothing_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Look for the "Authorization" header in the incoming request
        final String authHeader = request.getHeader("Authorization");

        // 2. If it's missing or doesn't start with "Bearer ", ignore it and move on
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the token by removing the "Bearer " prefix (7 characters)
        final String jwt = authHeader.substring(7);

        try {
            // 4. Ask our JwtUtil to extract the email (subject) from the token
            final String userEmail = jwtUtil.extractEmail(jwt);

            // 5. If we found an email, and the user isn't already authenticated in this session...
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 6. Verify the token hasn't expired and wasn't forged
                if (jwtUtil.validateToken(jwt)) {
                    // Create an authentication object (Spring Security needs this to know who is logged in)
                    UserDetails userDetails = User.withUsername(userEmail).password("").authorities(Collections.emptyList()).build();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 7. Save this authentication in the Security Context so the rest of the app knows the user is logged in!
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // If the token is fake or expired, it throws an error. We catch it here and just don't authenticate them.
            System.out.println("Invalid JWT Token: " + e.getMessage());
        }

        // 8. Continue processing the request
        filterChain.doFilter(request, response);
    }
}