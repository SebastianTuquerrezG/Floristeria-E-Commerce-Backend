package com.floridos.back_admin_management.order;

import com.floridos.back_admin_management.exception.OrderExceptions;
import com.floridos.back_admin_management.order.dto.CreateOrderRequest;
import com.floridos.back_admin_management.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /* ══════════ RUTAS CLIENTE ══════════ */

    /** POST /api/client/orders — crear orden desde el carrito */
    @PostMapping("/api/client/orders")
    public ResponseEntity<Order> createOrder(
            @Valid @RequestBody CreateOrderRequest req,
            @AuthenticationPrincipal User currentUser
    ) {
        Order created = orderService.createOrder(req, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** GET /api/client/orders — historial del cliente autenticado */
    @GetMapping("/api/client/orders")
    public ResponseEntity<List<Order>> clientOrders(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(orderService.findByCustomer(currentUser.getId()));
    }

    /** GET /api/client/orders/{id} — detalle de una orden del cliente */
    @GetMapping("/api/client/orders/{id}")
    public ResponseEntity<Order> clientOrderDetail(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        return orderService.findById(id)
                .filter(o -> o.getCustomer() != null &&
                        o.getCustomer().getId().equals(currentUser.getId()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* ══════════ RUTAS ADMIN ══════════ */

    /** GET /api/admin/orders?status=PENDING */
    @GetMapping("/api/admin/orders")
    public ResponseEntity<List<Order>> adminGetOrders(
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(orderService.findAll(Optional.ofNullable(status)));
    }

    /** GET /api/admin/orders/{id} */
    @GetMapping("/api/admin/orders/{id}")
    public ResponseEntity<Order> adminGetOrder(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** PUT /api/admin/orders/{id}/status — cambiar estado */
    @PutMapping("/api/admin/orders/{id}/status")
    public ResponseEntity<?> adminUpdateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String newStatus = body.get("status");
        if (newStatus == null || newStatus.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El campo 'status' es obligatorio"));
        }
        try {
            Order updated = orderService.updateStatus(id, newStatus);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (OrderExceptions.OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}