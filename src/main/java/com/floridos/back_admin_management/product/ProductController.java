package com.floridos.back_admin_management.product;

import com.floridos.back_admin_management.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService  productService;
    private final StorageService storageService;

    /* ══════════ RUTAS PÚBLICAS ══════════ */

    /** GET /api/public/products?category=FLORES&type=Rosas&occasion=Boda&inStock=true */
    @GetMapping("/api/public/products")
    public ResponseEntity<List<Product>> getPublicProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String occasion,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String care,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean inStock
    ) {
        return ResponseEntity.ok(productService.findAll(category, type, occasion, color, care, location, inStock));
    }

    /** GET /api/public/products/{slug} */
    @GetMapping("/api/public/products/{slug}")
    public ResponseEntity<Product> getProductBySlug(@PathVariable String slug) {
        return productService.findBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* ══════════ RUTAS ADMIN ══════════ */
    /** GET /api/admin/products */
    @GetMapping("/api/admin/products")
    public ResponseEntity<List<Product>> adminGetAll(
            @RequestParam(required = false) String category
    ) {
        return ResponseEntity.ok(productService.findAll(category, null, null, null, null, null, null));
    }

    /** POST /api/admin/products */
    @PostMapping("/api/admin/products")
    public ResponseEntity<Product> adminCreate(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product));
    }

    /** PUT /api/admin/products/{id} */
    @PutMapping("/api/admin/products/{id}")
    public ResponseEntity<Product> adminUpdate(@PathVariable Long id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.update(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** DELETE /api/admin/products/{id} */
    @DeleteMapping("/api/admin/products/{id}")
    public ResponseEntity<Void> adminDelete(@PathVariable Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** POST /api/admin/upload — subir imagen */
    @PostMapping("/api/admin/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = storageService.store(file);
        return ResponseEntity.ok(url);
    }
}
