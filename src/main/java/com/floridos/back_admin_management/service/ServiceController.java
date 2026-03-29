package com.floridos.back_admin_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServiceController {
    private final FloridosServiceService serviceService;

    /* ── Público ── */

    /**
     * GET /api/public/services?category=Bodas&scale=Grande
     * Usado por ServicesPage.tsx para cargar las cards
     */
    @GetMapping("/api/public/services")
    public ResponseEntity<List<FloridosService>> getPublicServices(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String scale
    ) {
        return ResponseEntity.ok(serviceService.findActive(category, scale));
    }

    @GetMapping("/api/public/services/{id}")
    public ResponseEntity<FloridosService> getPublicService(@PathVariable Long id) {
        return serviceService.findById(id)
                .filter(FloridosService::getActive)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* ── Admin ── */

    @GetMapping("/api/admin/services")
    public ResponseEntity<List<FloridosService>> adminGetAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String scale
    ) {
        return ResponseEntity.ok(serviceService.findActive(category, scale));
    }

    @PostMapping("/api/admin/services")
    public ResponseEntity<FloridosService> create(@RequestBody FloridosService service) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceService.create(service));
    }

    @PutMapping("/api/admin/services/{id}")
    public ResponseEntity<FloridosService> update(@PathVariable Long id, @RequestBody FloridosService service) {
        try {
            return ResponseEntity.ok(serviceService.update(id, service));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/admin/services/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            serviceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
