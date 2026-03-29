package com.floridos.back_admin_management.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/upload/image/")
public class StorageController {
    private final StorageService storageService;

    /**
     * POST /api/admin/upload
     * Body: multipart/form-data con campo "file"
     * Respuesta: { "url": "/uploads/uuid-nombre.jpg" }
     *
     * Uso desde React:
     *   const form = new FormData();
     *   form.append("file", file);
     *   const res = await fetch("/api/admin/upload", {
     *     method: "POST",
     *     headers: { Authorization: `Bearer ${token}` },
     *     body: form
     *   });
     *   const { url } = await res.json();
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> upload(
            @RequestParam("file") MultipartFile file
    ) {
        String url = storageService.store(file);
        return ResponseEntity.ok(Map.of("url", url));
    }

    /**
     * DELETE /api/admin/upload?url=/uploads/abc.jpg
     * Elimina el archivo del disco.
     */
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("url") String fileUrl) {
        storageService.delete(fileUrl);
        return ResponseEntity.noContent().build();
    }
}
