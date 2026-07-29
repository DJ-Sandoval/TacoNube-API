package com.dev.apitaconube.exception;

/**
 * Archivo vacio, formato no soportado, o error de IO al guardarlo/leerlo.
 */
public class ArchivoInvalidoException extends RuntimeException {

    public ArchivoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
