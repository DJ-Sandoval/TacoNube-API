package com.dev.apitaconube.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RegistroNegocioRequest(

        @NotNull
        @Valid
        NegocioRequest negocio,

        @NotNull
        @Valid
        UsuarioAdminRequest admin
) {
}
