package com.dev.apitaconube.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductoRequest(

        @NotBlank
        @Size(max = 150)
        String nombre,

        String descripcion,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal precio,

        @NotNull
        Long categoriaId,

        // Opcional: si no se envia, el service lo deja en true por defecto.
        Boolean disponible
) {
}
