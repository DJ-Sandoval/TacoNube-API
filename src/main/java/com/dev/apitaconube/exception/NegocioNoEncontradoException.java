package com.dev.apitaconube.exception;

/**
 * Caso raro: el negocio del JWT ya no existe en BD (eliminado despues de
 * emitido el token). Defensivo, no deberia pasar en operacion normal.
 */
public class NegocioNoEncontradoException extends RuntimeException {

    public NegocioNoEncontradoException(Long id) {
        super("No se encontro el negocio con id " + id);
    }
}
