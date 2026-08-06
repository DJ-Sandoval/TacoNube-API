package com.dev.apitaconube.repository;

import com.dev.apitaconube.domain.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findAllByNegocioIdAndActivoTrue(Long negocioId);

    List<Producto> findAllByNegocioIdAndCategoriaIdAndActivoTrue(Long negocioId, Long categoriaId);

    Optional<Producto> findByIdAndNegocioId(Long id, Long negocioId);
}
