package com.dev.apitaconube.service.interfaces;

import com.dev.apitaconube.dto.request.LoginRequest;
import com.dev.apitaconube.dto.response.LoginResponse;

public interface AuthService {

    /**
     * Busca el usuario por email (unico a nivel global), valida password
     * y estado activo, y genera el JWT con usuarioId, negocioId y rol.
     */
    LoginResponse login(LoginRequest request);
}
