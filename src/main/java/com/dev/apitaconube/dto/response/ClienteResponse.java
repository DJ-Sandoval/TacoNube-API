package com.dev.apitaconube.dto.response;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String nombre,
        String telefono,
        String email,
        String direccion,
        LocalDateTime fechaRegistro
) {
}
