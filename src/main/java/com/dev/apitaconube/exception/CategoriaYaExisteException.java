package com.dev.apitaconube.exception;

public class CategoriaYaExisteException extends RuntimeException {

    public CategoriaYaExisteException(String nombre) {
        super("Ya existe una categoria con el nombre \"" + nombre + "\" en tu negocio, usa otro.");
    }
}
