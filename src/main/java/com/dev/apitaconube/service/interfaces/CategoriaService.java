package com.dev.apitaconube.service.interfaces;

import com.dev.apitaconube.dto.request.CategoriaRequest;
import com.dev.apitaconube.dto.response.CategoriaResponse;

import java.util.List;

public interface CategoriaService {

    CategoriaResponse crear(Long negocioId, CategoriaRequest request);

    List<CategoriaResponse> listar(Long negocioId);

    CategoriaResponse obtener(Long negocioId, Long id);

    CategoriaResponse actualizar(Long negocioId, Long id, CategoriaRequest request);

    // Soft delete: no se borra la fila (hay productos que la referencian),
    // solo se marca activo=false.
    void eliminar(Long negocioId, Long id);
}
