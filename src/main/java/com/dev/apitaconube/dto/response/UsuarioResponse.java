package com.dev.apitaconube.dto.response;

public record UsuarioResponse(
        Long id,
        String nombre,
        String email,
        String rol
) {
}
