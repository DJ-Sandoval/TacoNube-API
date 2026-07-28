package com.dev.apitaconube.exception;

/**
 * Se lanza tanto si el email no existe, la contrasena no coincide, o la
 * cuenta esta inactiva. El mensaje es siempre el mismo a proposito: no
 * revelamos cual de esas tres cosas fallo (evita enumeracion de usuarios).
 */
public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException() {
        super("Email o contrasena incorrectos");
    }
}

