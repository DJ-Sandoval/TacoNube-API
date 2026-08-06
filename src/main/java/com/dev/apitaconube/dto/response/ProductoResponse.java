package com.dev.apitaconube.dto.response;
import java.math.BigDecimal;

public record ProductoResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Long categoriaId,
        String categoriaNombre,
        Boolean disponible,
        Boolean activo,
        String imagenUrl
) {
}
