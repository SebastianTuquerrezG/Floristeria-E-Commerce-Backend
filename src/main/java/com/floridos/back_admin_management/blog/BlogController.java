package com.floridos.back_admin_management.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    /* ── Público ── */

    @GetMapping("/api/public/blog")
    public ResponseEntity<List<BlogPost>> getPublished() {
        return ResponseEntity.ok(blogService.findPublished());
    }

    @GetMapping("/api/public/blog/{slug}")
    public ResponseEntity<BlogPost> getBySlug(@PathVariable String slug) {
        return blogService.findBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* ── Admin ── */

    @GetMapping("/api/admin/blog")
    public ResponseEntity<List<BlogPost>> adminGetAll() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @PostMapping("/api/admin/blog")
    public ResponseEntity<BlogPost> create(@RequestBody BlogPost post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogService.create(post));
    }

    @PutMapping("/api/admin/blog/{id}")
    public ResponseEntity<BlogPost> update(@PathVariable Long id, @RequestBody BlogPost post) {
        try {
            return ResponseEntity.ok(blogService.update(id, post));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/admin/blog/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            blogService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}