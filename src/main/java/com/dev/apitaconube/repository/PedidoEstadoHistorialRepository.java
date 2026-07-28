package com.dev.apitaconube.repository;

import com.dev.apitaconube.domain.entity.PedidoEstadoHistorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoEstadoHistorialRepository extends JpaRepository<PedidoEstadoHistorial, Long> {
}
