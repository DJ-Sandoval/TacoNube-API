package com.dev.apitaconube.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioAdminRequest(

        @NotBlank
        @Size(max = 150)
        String nombre,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        // Se cifra con BCrypt antes de guardarse; nunca se persiste en texto plano.
        @NotBlank
        @Size(min = 8, max = 100)
        String password
) {
}
