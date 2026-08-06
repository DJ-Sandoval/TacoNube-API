package com.dev.apitaconube.repository;

import com.dev.apitaconube.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findAllByNegocioIdAndActivoTrue(Long negocioId);

    // Sin filtro de activo: el admin puede querer editar/reactivar una
    // categoria inactiva.
    Optional<Categoria> findByIdAndNegocioId(Long id, Long negocioId);

    boolean existsByNegocioIdAndNombre(Long negocioId, String nombre);
}
