package com.dev.apitaconube.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Forma estandar de toda respuesta de error de la API.
 * `errores` solo se llena cuando el error es de validacion de campos
 * (400 por @Valid); en el resto de los casos va null.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> errores
) {
}
