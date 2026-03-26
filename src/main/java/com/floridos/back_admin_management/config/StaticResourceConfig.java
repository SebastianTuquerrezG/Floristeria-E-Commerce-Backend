package com.floridos.back_admin_management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Sirve los archivos subidos (imágenes) como recursos estáticos.
 * GET /uploads/nombre-archivo.jpg → archivo en ./uploads/
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${app.storage.local-path:./uploads}")
    private String storagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir     = Paths.get(storagePath).toAbsolutePath();
        String fileUri     = uploadDir.toUri().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(fileUri);
    }
}
