package com.floridos.back_admin_management.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<FloridosService, Long> {
    List<FloridosService> findByActiveTrue();
    List<FloridosService> findByActiveTrueAndCategory(String category);
    List<FloridosService> findByActiveTrueAndScale(String scale);
    List<FloridosService> findByActiveTrueAndCategoryAndScale(String category, String scale);
}
