package com.dev.apitaconube.service.impl;

import com.dev.apitaconube.domain.entity.Cliente;
import com.dev.apitaconube.dto.request.ClienteRequest;
import com.dev.apitaconube.dto.response.ClienteResponse;
import com.dev.apitaconube.exception.ClienteNoEncontradoException;
import com.dev.apitaconube.repository.ClienteRepository;
import com.dev.apitaconube.repository.NegocioRepository;
import com.dev.apitaconube.service.interfaces.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final NegocioRepository negocioRepository;

    @Override
    @Transactional
    public ClienteResponse crear(Long negocioId, ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .negocio(negocioRepository.getReferenceById(negocioId))
                .nombre(request.nombre())
                .telefono(request.telefono())
                .email(request.email())
                .direccion(request.direccion())
                .build();

        cliente = clienteRepository.save(cliente);
        return toResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> listar(Long negocioId) {
        return clienteRepository.findAllByNegocioId(negocioId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse obtener(Long negocioId, Long id) {
        return toResponse(buscarPorIdYNegocio(negocioId, id));
    }

    @Override
    @Transactional
    public ClienteResponse actualizar(Long negocioId, Long id, ClienteRequest request) {
        Cliente cliente = buscarPorIdYNegocio(negocioId, id);

        cliente.setNombre(request.nombre());
        cliente.setTelefono(request.telefono());
        cliente.setEmail(request.email());
        cliente.setDireccion(request.direccion());
        clienteRepository.save(cliente);

        return toResponse(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Long negocioId, Long id) {
        Cliente cliente = buscarPorIdYNegocio(negocioId, id);
        clienteRepository.delete(cliente);
    }

    private Cliente buscarPorIdYNegocio(Long negocioId, Long id) {
        return clienteRepository.findByIdAndNegocioId(id, negocioId)
                .orElseThrow(() -> new ClienteNoEncontradoException(id));
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getEmail(),
                cliente.getDireccion(),
                cliente.getCreatedAt()
        );
    }
}

