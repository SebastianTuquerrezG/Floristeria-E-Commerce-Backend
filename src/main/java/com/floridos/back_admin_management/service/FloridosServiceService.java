package com.floridos.back_admin_management.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FloridosServiceService {
    private final ServiceRepository serviceRepository;

    /* ── Consultas públicas ── */

    public List<FloridosService> findActive(String category, String scale) {
        if (category != null && scale != null)
            return serviceRepository.findByActiveTrueAndCategoryAndScale(category, scale);
        if (category != null)
            return serviceRepository.findByActiveTrueAndCategory(category);
        if (scale != null)
            return serviceRepository.findByActiveTrueAndScale(scale);
        return serviceRepository.findByActiveTrue();
    }

    public Optional<FloridosService> findById(Long id) {
        return serviceRepository.findById(id);
    }

    /* ── CRUD admin ── */

    @Transactional
    public FloridosService create(FloridosService service) {
        return serviceRepository.save(service);
    }

    @Transactional
    public FloridosService update(Long id, FloridosService incoming) {
        FloridosService s = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + id));

        if (incoming.getName()        != null) s.setName(incoming.getName());
        if (incoming.getCategory()    != null) s.setCategory(incoming.getCategory());
        if (incoming.getScale()       != null) s.setScale(incoming.getScale());
        if (incoming.getDescription() != null) s.setDescription(incoming.getDescription());
        if (incoming.getImageUrl()    != null) s.setImageUrl(incoming.getImageUrl());
        if (incoming.getTag()         != null) s.setTag(incoming.getTag());
        if (incoming.getIncludes()    != null) s.setIncludes(incoming.getIncludes());
        if (incoming.getActive()      != null) s.setActive(incoming.getActive());

        return serviceRepository.save(s);
    }

    @Transactional
    public void delete(Long id) {
        FloridosService service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + id));
        // Soft delete
        service.setActive(false);
        serviceRepository.save(service);
    }
}
