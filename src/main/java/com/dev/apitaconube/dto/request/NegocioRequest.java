package com.dev.apitaconube.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NegocioRequest(

        @NotBlank
        @Size(max = 150)
        String nombre,

        @Size(max = 20)
        String rfc,

        @Size(max = 255)
        String direccion,

        @Size(max = 20)
        String telefono,

        @NotBlank
        @Email
        @Size(max = 150)
        String email
) {
}