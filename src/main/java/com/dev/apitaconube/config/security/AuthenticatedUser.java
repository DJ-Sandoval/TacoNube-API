package com.dev.apitaconube.config.security;

/**
 * Principal que queda en el SecurityContext tras validar el JWT. No es un
 * UserDetails con carga a base de datos: todo lo que necesitamos (quien es,
 * de que negocio, con que rol) ya viene en los claims del token, asi que
 * cada request autenticado no pega contra la base de datos para esto.
 */
public record AuthenticatedUser(
        Long usuarioId,
        Long negocioId,
        String rol
) {
}
