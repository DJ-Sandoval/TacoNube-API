package com.dev.apitaconube.controller;

import com.dev.apitaconube.config.security.AuthenticatedUser;
import com.dev.apitaconube.dto.request.ProductoRequest;
import com.dev.apitaconube.dto.response.ProductoResponse;
import com.dev.apitaconube.service.interfaces.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam(required = false) Long categoriaId) {
        return ResponseEntity.ok(productoService.listar(authenticatedUser.negocioId(), categoriaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtener(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtener(authenticatedUser.negocioId(), id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponse> crear(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.crear(authenticatedUser.negocioId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponse> actualizar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizar(authenticatedUser.negocioId(), id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long id) {
        productoService.eliminar(authenticatedUser.negocioId(), id);
        return ResponseEntity.noContent().build();
    }
}

