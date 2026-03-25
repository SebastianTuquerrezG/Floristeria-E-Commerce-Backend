package com.floridos.back_admin_management.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/client")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getProfile() {
        return ResponseEntity.ok(Map.of("message", "perfil - implementar"));
    }

    @PutMapping("/me")
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(Map.of("message", "perfil actualizado - implementar", "payload", payload));
    }

    @PostMapping("/orders")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> order) {
        return ResponseEntity.status(201).body(Map.of("message", "orden creada - implementar", "order", order));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Object>> listOrders() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Map<String, Object>> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("message", "detalle orden - implementar", "id", id));
    }

    @GetMapping("/wishlist")
    public ResponseEntity<List<Object>> getWishlist() {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<Map<String, Object>> addToWishlist(@PathVariable Long productId) {
        return ResponseEntity.status(201).body(Map.of("message", "producto añadido - implementar", "productId", productId));
    }

    @DeleteMapping("/wishlist/{productId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long productId) {
        return ResponseEntity.noContent().build();
    }


}
