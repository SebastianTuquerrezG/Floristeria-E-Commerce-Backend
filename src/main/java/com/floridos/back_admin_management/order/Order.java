package com.floridos.back_admin_management.order;

import com.floridos.back_admin_management.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.floridos.back_admin_management.order.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<DeliveryGroup> deliveryGroups;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;                   // PENDING, CONFIRMED, etc.

    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal shipping;
    private BigDecimal total;
    private String couponCode;
    private String paymentMethod;

    // datos de contacto del cliente
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String contactCity;
    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
