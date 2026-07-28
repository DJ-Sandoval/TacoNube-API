package com.dev.apitaconube.dto.response;

public record RegistroNegocioResponse(
        NegocioResponse negocio,
        UsuarioResponse admin
) {
}

