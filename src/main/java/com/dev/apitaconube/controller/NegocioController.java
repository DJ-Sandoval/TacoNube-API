package com.dev.apitaconube.controller;

import com.dev.apitaconube.dto.request.RegistroNegocioRequest;
import com.dev.apitaconube.dto.response.RegistroNegocioResponse;
import com.dev.apitaconube.service.interfaces.NegocioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/negocios")
@RequiredArgsConstructor
public class NegocioController {

    private final NegocioService negocioService;

    // Publico (sin JWT): es el paso previo a tener credenciales. Cuando
    // armemos la seguridad, esta ruta va en la whitelist de endpoints publicos.
    @PostMapping("/registro")
    public ResponseEntity<RegistroNegocioResponse> registrarNegocio(
            @Valid @RequestBody RegistroNegocioRequest request) {
        RegistroNegocioResponse response = negocioService.registrarNegocio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
