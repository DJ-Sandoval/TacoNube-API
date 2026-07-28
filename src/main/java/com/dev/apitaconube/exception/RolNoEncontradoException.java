package com.dev.apitaconube.exception;

/**
 * Se lanza cuando un rol requerido por la logica de negocio (ej. "ADMIN")
 * no existe en la tabla roles. En condiciones normales esto no deberia
 * pasar: indica que el seed de roles no se corrio en este ambiente.
 */
public class RolNoEncontradoException extends RuntimeException {

    public RolNoEncontradoException(String nombreRol) {
        super("El rol \"" + nombreRol + "\" no existe en el sistema. Verifica el seed de datos de la tabla roles.");
    }
}
