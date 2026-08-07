package com.dev.apitaconube.service.interfaces;

import com.dev.apitaconube.dto.request.ClienteRequest;
import com.dev.apitaconube.dto.response.ClienteResponse;

import java.util.List;

public interface ClienteService {

    ClienteResponse crear(Long negocioId, ClienteRequest request);

    List<ClienteResponse> listar(Long negocioId);

    ClienteResponse obtener(Long negocioId, Long id);

    ClienteResponse actualizar(Long negocioId, Long id, ClienteRequest request);

    // Hard delete: a diferencia de categorias/productos, pedidos.cliente_id
    // usa ON DELETE SET NULL, asi que borrar un cliente no rompe el
    // historico de pedidos (solo pierde la referencia al cliente).
    void eliminar(Long negocioId, Long id);
}

