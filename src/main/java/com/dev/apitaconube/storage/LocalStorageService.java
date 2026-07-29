package com.dev.apitaconube.storage;
import com.dev.apitaconube.exception.ArchivoInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    @Value("${app.storage.upload-dir}")
    private String uploadDir;

    @Value("${app.storage.base-url}")
    private String baseUrl;

    @Override
    public String guardar(MultipartFile archivo, String carpeta) {
        try {
            String extension = obtenerExtension(archivo.getOriginalFilename());
            String nombreArchivo = UUID.randomUUID() + extension;

            Path directorioDestino = Paths.get(uploadDir, carpeta);
            Files.createDirectories(directorioDestino);

            Path rutaDestino = directorioDestino.resolve(nombreArchivo);
            archivo.transferTo(rutaDestino);

            String rutaRelativa = carpeta + "/" + nombreArchivo;
            return baseUrl + "/" + rutaRelativa;
        } catch (IOException e) {
            throw new ArchivoInvalidoException("No se pudo guardar el archivo: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String urlPublica) {
        if (urlPublica == null || !urlPublica.startsWith(baseUrl)) {
            return;
        }
        try {
            String rutaRelativa = urlPublica.substring(baseUrl.length() + 1);
            Path ruta = Paths.get(uploadDir, rutaRelativa);
            Files.deleteIfExists(ruta);
        } catch (IOException e) {
            // No relanzamos: que falle el borrado del icono viejo no debe
            // tumbar la actualizacion del nuevo. En un proyecto real, log aqui.
        }
    }

    private String obtenerExtension(String nombreOriginal) {
        if (nombreOriginal == null || !nombreOriginal.contains(".")) {
            return "";
        }
        return nombreOriginal.substring(nombreOriginal.lastIndexOf('.'));
    }
}
