package com.dev.apitaconube.dto.response;

import com.dev.apitaconube.domain.enums.EstadoNegocio;

public record NegocioResponse(
        Long id,
        String nombre,
        String email,
        EstadoNegocio estado
) {
}
