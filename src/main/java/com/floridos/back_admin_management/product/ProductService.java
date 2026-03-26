package com.floridos.back_admin_management.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /* ── consultas públicas ── */

    public List<Product> findAll(String category, String type, String occasion,
                                 String color, String care, String location,
                                 Boolean inStock) {
        // Filtrado en memoria — para producción usa Specification o Querydsl
        return productRepository.findAll().stream()
                .filter(p -> category == null || p.getCategory().name().equalsIgnoreCase(category))
                .filter(p -> type     == null || type.equalsIgnoreCase(p.getType()))
                .filter(p -> occasion == null || occasion.equalsIgnoreCase(p.getOccasion()))
                .filter(p -> color    == null || color.equalsIgnoreCase(p.getColor()))
                .filter(p -> care     == null || care.equalsIgnoreCase(p.getCare()))
                .filter(p -> location == null || location.equalsIgnoreCase(p.getLocation()))
                .filter(p -> inStock  == null || inStock.equals(p.getInStock()))
                .toList();
    }

    public Optional<Product> findBySlug(String slug) {
        return productRepository.findBySlug(slug);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /* ── CRUD admin ── */

    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, Product incoming) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));

        existing.setName(incoming.getName());
        existing.setDescription(incoming.getDescription());
        existing.setPrice(incoming.getPrice());
        existing.setRating(incoming.getRating());
        existing.setInStock(incoming.getInStock());
        existing.setImageUrl(incoming.getImageUrl());
        existing.setSlug(incoming.getSlug());
        existing.setCategory(incoming.getCategory());
        existing.setType(incoming.getType());
        existing.setOccasion(incoming.getOccasion());
        existing.setColor(incoming.getColor());
        existing.setLocation(incoming.getLocation());
        existing.setCare(incoming.getCare());

        return productRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado: " + id);
        }
        productRepository.deleteById(id);
    }
}
