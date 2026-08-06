package com.dev.apitaconube.dto.response;

public record CategoriaResponse(
        Long id,
        String nombre,
        String descripcion,
        Boolean activo
) {
}
