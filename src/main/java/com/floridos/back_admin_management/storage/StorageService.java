package com.floridos.back_admin_management.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {
    @Value("${app.storage.local-path:./uploads}")
    private String storagePath;

    /**
     * Guarda el archivo en disco y devuelve la URL pública relativa.
     * Ej: /uploads/abc123-imagen.jpg
     */
    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }

        String originalName = file.getOriginalFilename();
        String extension    = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String filename = UUID.randomUUID() + extension;

        try {
            Path uploadDir = Paths.get(storagePath);
            Files.createDirectories(uploadDir);
            Files.copy(file.getInputStream(), uploadDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage());
        }

        return "/uploads/" + filename;
    }

    public void delete(String fileUrl) {
        if (fileUrl == null || !fileUrl.startsWith("/uploads/")) return;
        String filename = fileUrl.replace("/uploads/", "");
        try {
            Files.deleteIfExists(Paths.get(storagePath, filename));
        } catch (IOException e) {
            // log pero no lanzar — el archivo puede ya no existir
        }
    }
}
