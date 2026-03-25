package com.floridos.back_admin_management.auth;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody java.util.Map<String, Object> payload) {
        return ResponseEntity.ok("Register successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody java.util.Map<String, Object> payload) {
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody java.util.Map<String, Object> payload) {
        return ResponseEntity.ok("Refresh successful");
    }
}
