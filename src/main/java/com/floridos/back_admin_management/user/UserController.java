package com.floridos.back_admin_management.user;

import com.floridos.back_admin_management.product.Product;
import com.floridos.back_admin_management.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequiredArgsConstructor
public class UserController {
    private final UserService       userService;
    private final UserRepository    userRepository;
    private final ProductRepository productRepository;
    private final WishListRepository wishlistRepository;

    /* ══════════ PERFIL DEL CLIENTE ══════════ */

    /** GET /api/client/me */
    @GetMapping("/api/client/me")
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(UserProfileResponse.from(currentUser));
    }

    /** PUT /api/client/me */
    @PutMapping("/api/client/me")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @AuthenticationPrincipal User currentUser,
            @RequestBody UserService.UserUpdateRequest req
    ) {
        User updated = userService.updateProfile(currentUser.getId(), req);
        return ResponseEntity.ok(UserProfileResponse.from(updated));
    }

    /* ══════════ WISHLIST ══════════ */

    /** GET /api/client/wishlist */
    @GetMapping("/api/client/wishlist")
    public ResponseEntity<List<Product>> getWishlist(@AuthenticationPrincipal User currentUser) {
        List<Product> products = wishlistRepository.findByUserId(currentUser.getId())
                .stream()
                .map(WishListItem::getProduct)
                .toList();
        return ResponseEntity.ok(products);
    }

    /** POST /api/client/wishlist/{productId} */
    @PostMapping("/api/client/wishlist/{productId}")
    public ResponseEntity<?> addToWishlist(
            @PathVariable Long productId,
            @AuthenticationPrincipal User currentUser
    ) {
        if (wishlistRepository.existsByUserIdAndProductId(currentUser.getId(), productId)) {
            return ResponseEntity.ok(Map.of("message", "Ya está en favoritos"));
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        WishListItem item = WishListItem.builder()
                .user(currentUser)
                .product(product)
                .build();
        wishlistRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Añadido a favoritos"));
    }

    /** DELETE /api/client/wishlist/{productId} */
    @DeleteMapping("/api/client/wishlist/{productId}")
    public ResponseEntity<Void> removeFromWishlist(
            @PathVariable Long productId,
            @AuthenticationPrincipal User currentUser
    ) {
        wishlistRepository.deleteByUserIdAndProductId(currentUser.getId(), productId);
        return ResponseEntity.noContent().build();
    }

    /* ══════════ ADMIN — GESTIÓN DE USUARIOS ══════════ */

    /** GET /api/admin/users */
    @GetMapping("/api/admin/users")
    public ResponseEntity<List<UserProfileResponse>> adminGetAllUsers() {
        return ResponseEntity.ok(userService.findAll().stream()
                .map(UserProfileResponse::from)
                .toList());
    }

    /** PUT /api/admin/users/{id}/role */
    @PutMapping("/api/admin/users/{id}/role")
    public ResponseEntity<?> adminChangeRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String roleName = body.get("role");
        if (roleName == null) return ResponseEntity.badRequest().body("Campo 'role' requerido");
        try {
            Role newRole = Role.valueOf(roleName.toUpperCase());
            User updated = userService.changeRole(id, newRole);
            return ResponseEntity.ok(UserProfileResponse.from(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Rol inválido: " + roleName);
        }
    }

    /** DELETE /api/admin/users/{id} — desactivar usuario */
    @DeleteMapping("/api/admin/users/{id}")
    public ResponseEntity<Void> adminDeactivateUser(@PathVariable Long id) {
        userService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    /* ── DTO de respuesta (nunca devuelve el password) ── */
    record UserProfileResponse(Long id, String name, String email, String phone, String city, String role, Boolean active) {
        static UserProfileResponse from(User u) {
            return new UserProfileResponse(u.getId(), u.getName(), u.getEmail(),
                    u.getPhone(), u.getCity(), u.getRole().name(), u.isEnabled());
        }
    }
}