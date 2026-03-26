package com.floridos.back_admin_management.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "delivery_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private String groupName;

    private String fragrance;

    @Column(columnDefinition = "TEXT")
    private String cardMessage;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    private LocalTime deliveryTime;

    @OneToMany(mappedBy = "deliveryGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
}