package com.floridos.back_admin_management.service;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "floridos_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloridosService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;         // Bodas, Corporativo, Celebración, etc.

    @Column(nullable = false)
    private String scale;            // Pequeño, Mediano, Grande, Recurrente

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;
    private String tag;              // "Más solicitado", "Novedad", null

    @Builder.Default
    private Boolean active = true;

    // Lista de lo que incluye el servicio, guardada como JSON string
    // En producción usa @ElementCollection o una tabla separada
    @ElementCollection
    @CollectionTable(name = "service_includes", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "include_item")
    private List<String> includes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
