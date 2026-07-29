package com.dev.apitaconube.storage;


import org.springframework.web.multipart.MultipartFile;

/**
 * Abstraccion sobre donde vive el archivo fisico. Hoy la unica implementacion
 * es LocalStorageService (disco local). El dia que migremos a DigitalOcean
 * Spaces, se agrega una SpacesStorageService que implemente esto mismo (API
 * compatible con S3) y se cambia el bean activo por configuracion — nada del
 * resto del sistema (NegocioService, controller) se entera del cambio.
 */
public interface StorageService {

    /**
     * Guarda el archivo bajo la subcarpeta indicada y devuelve la URL
     * publica desde la que se puede consultar despues.
     */
    String guardar(MultipartFile archivo, String carpeta);

    /**
     * Elimina un archivo previamente guardado, a partir de la URL que
     * devolvio guardar(). No lanza si el archivo ya no existe.
     */
    void eliminar(String urlPublica);
}
