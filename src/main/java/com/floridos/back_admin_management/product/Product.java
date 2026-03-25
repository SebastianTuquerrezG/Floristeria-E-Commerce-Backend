package com.floridos.back_admin_management.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private Double rating;
    private Boolean inStock;
    private String imageUrl;
    private String slug;

    @Enumerated(EnumType.STRING)
    private Category category;       // FLORES | PLANTAS

    // flores
    private String type;
    private String occasion;
    private String color;

    // plantas
    private String location;
    private String care;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
