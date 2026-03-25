package com.floridos.back_admin_management.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.sun.beans.introspect.PropertyInfo.Name.required;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = new ArrayList<>();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/products")
    public ResponseEntity<List<Product>> getPublicProducts(
            org.springframework.web.bind.annotation.RequestParam(required = false) String type,
            org.springframework.web.bind.annotation.RequestParam(required = false) String occasion) {
        List<Product> products = new ArrayList<>();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/products/{slug}")
    public ResponseEntity<Product> getPublicProductBySlug(
            org.springframework.web.bind.annotation.PathVariable String slug) {
        return ResponseEntity.notFound().build();
    }
    // Admin endpoints
    @GetMapping("/admin/products")
    public ResponseEntity<List<Product>> getAdminProducts() {
        List<Product> products = new ArrayList<>();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/admin/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // implementar lógica de creación
        return ResponseEntity.ok(product);
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        // implementar lógica de actualización
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // implementar lógica de eliminación
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        // implementar almacenamiento real; aquí se devuelve una URL simulada
        String url = "/uploads/" + (file != null ? file.getOriginalFilename() : "unknown");
        return ResponseEntity.ok(url);
    }

}
