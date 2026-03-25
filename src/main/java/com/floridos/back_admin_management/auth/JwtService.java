package com.floridos.back_admin_management.auth;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String generateToken(String username) {
        // Placeholder token generation logic
        return "token-for-" + username;
    }

    public boolean validateToken(String token) {
        // Placeholder token validation logic
        return token.startsWith("token-for-");
    }
}
