package com.dev.apitaconube.repository;

import com.dev.apitaconube.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findAllByNegocioId(Long negocioId);

    Optional<Cliente> findByIdAndNegocioId(Long id, Long negocioId);
}
