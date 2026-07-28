package com.dev.apitaconube.dto.response;

public record LoginResponse(
        String token,
        String tipo,          // "Bearer"
        Long usuarioId,
        Long negocioId,
        String nombreUsuario,
        String negocioNombre,
        String rol
) {
}
