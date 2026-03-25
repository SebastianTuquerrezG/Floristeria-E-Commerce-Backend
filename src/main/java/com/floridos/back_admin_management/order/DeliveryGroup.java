package com.floridos.back_admin_management.order;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "delivery_groups")
public class DeliveryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    private String groupName;         // "Entrega 1", "Para mamá"
    private String fragrance;
    private String cardMessage;
    private String address;
    private LocalDate deliveryDate;
    private LocalTime deliveryTime;

    @OneToMany(mappedBy = "deliveryGroup", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
