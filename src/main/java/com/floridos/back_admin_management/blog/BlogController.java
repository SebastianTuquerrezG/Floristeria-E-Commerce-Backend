package com.floridos.back_admin_management.blog;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/admin/blog")
public class BlogController {

    @org.springframework.web.bind.annotation.PostMapping
    public org.springframework.http.ResponseEntity<java.util.Map<String, Object>> create(
            @org.springframework.web.bind.annotation.RequestBody java.util.Map<String, Object> body) {

        java.util.Map<String, Object> response = new java.util.HashMap<>();
        // Aquí normalmente se guardaría el recurso y se obtendría el id real
        Long generatedId = 1L;
        response.put("id", generatedId);
        response.put("data", body);

        return org.springframework.http.ResponseEntity
                .status(org.springframework.http.HttpStatus.CREATED)
                .body(response);
    }

    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    public org.springframework.http.ResponseEntity<java.util.Map<String, Object>> update(
            @org.springframework.web.bind.annotation.PathVariable Long id,
            @org.springframework.web.bind.annotation.RequestBody java.util.Map<String, Object> body) {

        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("id", id);
        response.put("data", body);

        return org.springframework.http.ResponseEntity
                .status(org.springframework.http.HttpStatus.OK)
                .body(response);
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<Void> delete(
            @org.springframework.web.bind.annotation.PathVariable Long id) {

        // Aquí normalmente se eliminaría el recurso
        return org.springframework.http.ResponseEntity.noContent().build();
    }
}