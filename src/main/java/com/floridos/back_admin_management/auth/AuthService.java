package com.floridos.back_admin_management.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean authenticate(String username, String password) {
        // Placeholder authentication logic
        return "admin".equals(username) && "password".equals(password);
    }
}
