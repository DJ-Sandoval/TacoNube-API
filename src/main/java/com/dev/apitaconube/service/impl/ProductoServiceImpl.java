package com.dev.apitaconube.service.impl;

import com.dev.apitaconube.domain.entity.Categoria;
import com.dev.apitaconube.domain.entity.Producto;
import com.dev.apitaconube.dto.request.ProductoRequest;
import com.dev.apitaconube.dto.response.ProductoResponse;
import com.dev.apitaconube.exception.CategoriaNoEncontradaException;
import com.dev.apitaconube.exception.ProductoNoEncontradoException;
import com.dev.apitaconube.repository.CategoriaRepository;
import com.dev.apitaconube.repository.NegocioRepository;
import com.dev.apitaconube.repository.ProductoRepository;
import com.dev.apitaconube.service.interfaces.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final NegocioRepository negocioRepository;

    @Override
    @Transactional
    public ProductoResponse crear(Long negocioId, ProductoRequest request) {
        Categoria categoria = buscarCategoriaDelNegocio(negocioId, request.categoriaId());

        Producto producto = Producto.builder()
                .negocio(negocioRepository.getReferenceById(negocioId))
                .categoria(categoria)
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .precio(request.precio())
                .disponible(request.disponible() != null ? request.disponible() : true)
                .activo(true)
                .build();

        producto = productoRepository.save(producto);
        return toResponse(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(Long negocioId, Long categoriaId) {
        List<Producto> productos = categoriaId != null
                ? productoRepository.findAllByNegocioIdAndCategoriaIdAndActivoTrue(negocioId, categoriaId)
                : productoRepository.findAllByNegocioIdAndActivoTrue(negocioId);

        return productos.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtener(Long negocioId, Long id) {
        return toResponse(buscarPorIdYNegocio(negocioId, id));
    }

    @Override
    @Transactional
    public ProductoResponse actualizar(Long negocioId, Long id, ProductoRequest request) {
        Producto producto = buscarPorIdYNegocio(negocioId, id);

        // Si cambio de categoria, se revalida que la nueva tambien sea del
        // mismo negocio (misma proteccion que en crear()).
        if (!producto.getCategoria().getId().equals(request.categoriaId())) {
            producto.setCategoria(buscarCategoriaDelNegocio(negocioId, request.categoriaId()));
        }

        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        if (request.disponible() != null) {
            producto.setDisponible(request.disponible());
        }

        productoRepository.save(producto);
        return toResponse(producto);
    }

    @Override
    @Transactional
    public void eliminar(Long negocioId, Long id) {
        Producto producto = buscarPorIdYNegocio(negocioId, id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private Categoria buscarCategoriaDelNegocio(Long negocioId, Long categoriaId) {
        // Reutiliza CategoriaNoEncontradaException: si la categoria es de
        // otro negocio, para este tenant "no existe" (no se distingue de un
        // id inventado, para no filtrar informacion de otros negocios).
        return categoriaRepository.findByIdAndNegocioId(categoriaId, negocioId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
    }

    private Producto buscarPorIdYNegocio(Long negocioId, Long id) {
        return productoRepository.findByIdAndNegocioId(id, negocioId)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    private ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCategoria().getId(),
                producto.getCategoria().getNombre(),
                producto.getDisponible(),
                producto.getActivo(),
                producto.getImagenUrl()
        );
    }
}

