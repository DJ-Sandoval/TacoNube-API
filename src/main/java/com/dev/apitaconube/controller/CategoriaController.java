package com.dev.apitaconube.controller;

import com.dev.apitaconube.config.security.AuthenticatedUser;
import com.dev.apitaconube.dto.request.CategoriaRequest;
import com.dev.apitaconube.dto.response.CategoriaResponse;
import com.dev.apitaconube.service.interfaces.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    // Lectura: cualquier usuario autenticado del negocio (mesero/cajero/cocina
    // tambien necesitan ver el catalogo, no solo el admin).
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(categoriaService.listar(authenticatedUser.negocioId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> obtener(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obtener(authenticatedUser.negocioId(), id));
    }

    // Escritura: solo ADMIN administra el catalogo.
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponse> crear(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse response = categoriaService.crear(authenticatedUser.negocioId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponse> actualizar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.actualizar(authenticatedUser.negocioId(), id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long id) {
        categoriaService.eliminar(authenticatedUser.negocioId(), id);
        return ResponseEntity.noContent().build();
    }
}

