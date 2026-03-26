package com.floridos.back_admin_management.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findBySlug(String slug);
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryAndType(Category category, String type);
    List<Product> findByCategoryAndOccasion(Category category, String occasion);
    List<Product> findByInStockTrue();
}
