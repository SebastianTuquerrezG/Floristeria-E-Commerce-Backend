package com.floridos.back_admin_management.config;

import com.floridos.back_admin_management.order.Order;
import com.floridos.back_admin_management.order.OrderRepository;
import com.floridos.back_admin_management.order.OrderStatus;
import com.floridos.back_admin_management.product.ProductRepository;
import com.floridos.back_admin_management.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/stats")
@RequiredArgsConstructor
public class AdminStatsController {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * GET /api/admin/stats
     * Devuelve métricas para el dashboard del admin.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getStats() {

        List<Order> allOrders = orderRepository.findAll();

        long totalOrders   = allOrders.size();
        long pendingOrders = allOrders.stream().filter(o -> o.getStatus() == OrderStatus.PENDING).count();
        long confirmedOrders = allOrders.stream().filter(o -> o.getStatus() == OrderStatus.CONFIRMED).count();

        BigDecimal totalRevenue = allOrders.stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Últimos 30 días
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        BigDecimal revenueThisMonth = allOrders.stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().isAfter(thirtyDaysAgo))
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalProducts  = productRepository.count();
        long outOfStock     = productRepository.findAll().stream()
                .filter(p -> Boolean.FALSE.equals(p.getInStock())).count();

        long totalCustomers = userRepository.count();

        return ResponseEntity.ok(Map.of(
                "orders", Map.of(
                        "total",     totalOrders,
                        "pending",   pendingOrders,
                        "confirmed", confirmedOrders
                ),
                "revenue", Map.of(
                        "total",      totalRevenue,
                        "thisMonth",  revenueThisMonth
                ),
                "products", Map.of(
                        "total",      totalProducts,
                        "outOfStock", outOfStock
                ),
                "customers", Map.of(
                        "total", totalCustomers
                )
        ));
    }
}
