package com.floridos.back_admin_management.order;

import com.floridos.back_admin_management.exception.OrderExceptions;
import com.floridos.back_admin_management.order.dto.CreateOrderRequest;
import com.floridos.back_admin_management.order.dto.DeliveryGroupRequest;
import com.floridos.back_admin_management.order.dto.OrderItemRequest;
import com.floridos.back_admin_management.product.Product;
import com.floridos.back_admin_management.product.ProductRepository;
import com.floridos.back_admin_management.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository   orderRepository;
    private final ProductRepository productRepository;

    private static final BigDecimal SHIPPING_THRESHOLD = BigDecimal.valueOf(150_000);
    private static final BigDecimal SHIPPING_COST      = BigDecimal.valueOf(12_000);

    /* ─── Crear orden (desde el carrito del cliente) ─── */
    @Transactional
    public Order createOrder(CreateOrderRequest req, User customer) {

        List<DeliveryGroup> groups = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (DeliveryGroupRequest groupReq : req.getDeliveryGroups()) {

            DeliveryGroup group = DeliveryGroup.builder()
                    .groupName(groupReq.getGroupName())
                    .fragrance(groupReq.getFragrance())
                    .cardMessage(groupReq.getCardMessage())
                    .address(groupReq.getAddress())
                    .deliveryDate(groupReq.getDeliveryDate())
                    .deliveryTime(groupReq.getDeliveryTime())
                    .build();

            List<OrderItem> items = new ArrayList<>();
            for (OrderItemRequest itemReq : groupReq.getItems()) {
                Product product = productRepository.findById(itemReq.getProductId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemReq.getProductId()));

                OrderItem item = OrderItem.builder()
                        .deliveryGroup(group)
                        .product(product)
                        .quantity(itemReq.getQuantity())
                        .unitPrice(product.getPrice())
                        .build();

                items.add(item);
                subtotal = subtotal.add(item.getSubtotal());
            }
            group.setItems(items);
            groups.add(group);
        }

        BigDecimal discount = BigDecimal.ZERO;
        if (req.getCouponCode() != null) {
            discount = applyCoupon(req.getCouponCode(), subtotal);
        }

        BigDecimal shipping = subtotal.compareTo(SHIPPING_THRESHOLD) >= 0
                ? BigDecimal.ZERO
                : SHIPPING_COST;

        BigDecimal total = subtotal.subtract(discount).add(shipping);

        Order order = Order.builder()
                .customer(customer)
                .status(OrderStatus.PENDING)
                .subtotal(subtotal)
                .discount(discount)
                .shipping(shipping)
                .total(total)
                .couponCode(req.getCouponCode())
                .paymentMethod(req.getPaymentMethod())
                .contactName(req.getContactName())
                .contactPhone(req.getContactPhone())
                .contactEmail(req.getContactEmail())
                .contactCity(req.getContactCity())
                .notes(req.getNotes())
                .build();

        // Enlazar grupos con la orden
        for (DeliveryGroup g : groups) {
            g.setOrder(order);
        }
        order.setDeliveryGroups(groups);

        return orderRepository.save(order);
    }

    /* ─── Consultas ─── */

    public List<Order> findAll(Optional<String> status) {
        return status
                .map(s -> orderRepository.findByStatus(OrderStatus.valueOf(s.toUpperCase())))
                .orElse(orderRepository.findAllByOrderByCreatedAtDesc());
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findByCustomer(Long customerId) {
        return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    /* ─── Cambio de estado (admin) ─── */
    @Transactional
    public Order updateStatus(Long id, String newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(OrderExceptions.OrderNotFoundException::new);

        OrderStatus status;
        try {
            status = OrderStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + newStatus);
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }

    /* ─── Cupones (simplificado — en producción usar BD) ─── */
    private BigDecimal applyCoupon(String code, BigDecimal subtotal) {
        return switch (code.toUpperCase()) {
            case "FLORIDOS10" -> subtotal.multiply(BigDecimal.valueOf(0.10));
            case "BIENVENIDA" -> subtotal.multiply(BigDecimal.valueOf(0.15));
            default           -> BigDecimal.ZERO;
        };
    }
}
