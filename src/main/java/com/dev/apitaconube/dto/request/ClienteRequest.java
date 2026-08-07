package com.dev.apitaconube.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequest(

        @NotBlank
        @Size(max = 150)
        String nombre,

        @Size(max = 20)
        String telefono,

        @Email
        @Size(max = 150)
        String email,

        @Size(max = 255)
        String direccion
) {
}
