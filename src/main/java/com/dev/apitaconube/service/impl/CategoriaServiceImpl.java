package com.dev.apitaconube.service.impl;

import com.dev.apitaconube.domain.entity.Categoria;
import com.dev.apitaconube.dto.request.CategoriaRequest;
import com.dev.apitaconube.dto.response.CategoriaResponse;
import com.dev.apitaconube.exception.CategoriaNoEncontradaException;
import com.dev.apitaconube.exception.CategoriaYaExisteException;
import com.dev.apitaconube.repository.CategoriaRepository;
import com.dev.apitaconube.repository.NegocioRepository;
import com.dev.apitaconube.service.interfaces.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final NegocioRepository negocioRepository;

    @Override
    @Transactional
    public CategoriaResponse crear(Long negocioId, CategoriaRequest request) {
        if (categoriaRepository.existsByNegocioIdAndNombre(negocioId, request.nombre())) {
            throw new CategoriaYaExisteException(request.nombre());
        }

        Categoria categoria = Categoria.builder()
                // getReferenceById: no hace SELECT, solo arma el proxy para
                // la FK. El negocio ya se valido al emitir el JWT.
                .negocio(negocioRepository.getReferenceById(negocioId))
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .activo(true)
                .build();

        categoria = categoriaRepository.save(categoria);
        return toResponse(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar(Long negocioId) {
        return categoriaRepository.findAllByNegocioIdAndActivoTrue(negocioId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponse obtener(Long negocioId, Long id) {
        return toResponse(buscarPorIdYNegocio(negocioId, id));
    }

    @Override
    @Transactional
    public CategoriaResponse actualizar(Long negocioId, Long id, CategoriaRequest request) {
        Categoria categoria = buscarPorIdYNegocio(negocioId, id);

        boolean cambioNombre = !categoria.getNombre().equalsIgnoreCase(request.nombre());
        if (cambioNombre && categoriaRepository.existsByNegocioIdAndNombre(negocioId, request.nombre())) {
            throw new CategoriaYaExisteException(request.nombre());
        }

        categoria.setNombre(request.nombre());
        categoria.setDescripcion(request.descripcion());
        categoriaRepository.save(categoria);

        return toResponse(categoria);
    }

    @Override
    @Transactional
    public void eliminar(Long negocioId, Long id) {
        Categoria categoria = buscarPorIdYNegocio(negocioId, id);
        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }

    private Categoria buscarPorIdYNegocio(Long negocioId, Long id) {
        // findByIdAndNegocioId (no solo findById) es lo que impide que un
        // negocio pueda leer/editar categorias de otro cambiando el id en la URL.
        return categoriaRepository.findByIdAndNegocioId(id, negocioId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(id));
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getActivo()
        );
    }
}