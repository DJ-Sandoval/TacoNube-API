package com.dev.apitaconube.controller;

import com.dev.apitaconube.config.security.AuthenticatedUser;
import com.dev.apitaconube.dto.request.ClienteRequest;
import com.dev.apitaconube.dto.response.ClienteResponse;
import com.dev.apitaconube.service.interfaces.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    // A diferencia del catalogo (categorias/productos), un cliente lo puede
    // dar de alta o editar cualquier usuario autenticado del negocio
    // (mesero/cajero tomando un pedido), no solo el ADMIN.

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(clienteService.listar(authenticatedUser.negocioId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtener(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtener(authenticatedUser.negocioId(), id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = clienteService.crear(authenticatedUser.negocioId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.actualizar(authenticatedUser.negocioId(), id, request));
    }

    // Borrado si restringido a ADMIN: evita que cualquier mesero borre
    // historial de clientes por accidente.
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long id) {
        clienteService.eliminar(authenticatedUser.negocioId(), id);
        return ResponseEntity.noContent().build();
    }
}

