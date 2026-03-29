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

    /**
     * PATCH semántico: solo pisa los campos que vienen no-nulos en el body.
     * Así un PUT parcial { "name": "...", "price": 48000 } no borra
     * description, imageUrl, occasion, etc.
     */
    @Transactional
    public Product update(Long id, Product incoming) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));

        // Solo actualiza si el campo viene en el request (no es null)
        if (incoming.getName()        != null) p.setName(incoming.getName());
        if (incoming.getDescription() != null) p.setDescription(incoming.getDescription());
        if (incoming.getPrice()       != null) p.setPrice(incoming.getPrice());
        if (incoming.getRating()      != null) p.setRating(incoming.getRating());
        if (incoming.getInStock()     != null) p.setInStock(incoming.getInStock());
        if (incoming.getImageUrl()    != null) p.setImageUrl(incoming.getImageUrl());
        if (incoming.getSlug()        != null) p.setSlug(incoming.getSlug());
        if (incoming.getCategory()    != null) p.setCategory(incoming.getCategory());
        if (incoming.getType()        != null) p.setType(incoming.getType());
        if (incoming.getOccasion()    != null) p.setOccasion(incoming.getOccasion());
        if (incoming.getColor()       != null) p.setColor(incoming.getColor());
        if (incoming.getLocation()    != null) p.setLocation(incoming.getLocation());
        if (incoming.getCare()        != null) p.setCare(incoming.getCare());

        return productRepository.save(p);
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado: " + id);
        }
        productRepository.deleteById(id);
    }
}
