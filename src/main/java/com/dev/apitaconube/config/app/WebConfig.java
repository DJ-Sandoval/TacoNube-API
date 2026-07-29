package com.dev.apitaconube.config.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Mapea /uploads/** del lado HTTP al directorio real en disco donde
 * LocalStorageService guarda los archivos. Solo aplica mientras el storage
 * sea local; al migrar a DigitalOcean Spaces, las URLs seran del propio
 * Spaces/CDN y esto deja de ser necesario.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.storage.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}