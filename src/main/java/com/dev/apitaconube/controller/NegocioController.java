package com.dev.apitaconube.controller;

import com.dev.apitaconube.config.security.AuthenticatedUser;
import com.dev.apitaconube.dto.request.RegistroNegocioRequest;
import com.dev.apitaconube.dto.response.NegocioResponse;
import com.dev.apitaconube.dto.response.RegistroNegocioResponse;
import com.dev.apitaconube.service.interfaces.NegocioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // El negocio a actualizar sale del JWT (authenticatedUser.negocioId()),
    // nunca de un parametro en la URL: asi evitamos que un admin de un
    // negocio pueda subir/pisar el icono de otro negocio.
    @PostMapping(value = "/icono", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NegocioResponse> actualizarIcono(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam("archivo") MultipartFile archivo) {
        NegocioResponse response = negocioService.actualizarIcono(authenticatedUser.negocioId(), archivo);
        return ResponseEntity.ok(response);
    }
}
