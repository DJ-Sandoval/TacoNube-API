package com.dev.apitaconube.exception;

/**
 * Se lanza cuando se intenta registrar un usuario con un email que ya existe
 * en el sistema. El email es unico a nivel global (no solo por negocio),
 * porque el login se hace solo con email+password.
 */
public class EmailYaRegistradoException extends RuntimeException {

    public EmailYaRegistradoException(String email) {
        super("Este email ya esta en uso, por favor usa otro.");
    }
}
