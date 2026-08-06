package com.dev.apitaconube.service.interfaces;

import com.dev.apitaconube.dto.request.ProductoRequest;
import com.dev.apitaconube.dto.response.ProductoResponse;

import java.util.List;

public interface ProductoService {

    ProductoResponse crear(Long negocioId, ProductoRequest request);

    // categoriaId nulo = sin filtro, trae todos los productos del negocio.
    List<ProductoResponse> listar(Long negocioId, Long categoriaId);

    ProductoResponse obtener(Long negocioId, Long id);

    ProductoResponse actualizar(Long negocioId, Long id, ProductoRequest request);

    void eliminar(Long negocioId, Long id);
}
