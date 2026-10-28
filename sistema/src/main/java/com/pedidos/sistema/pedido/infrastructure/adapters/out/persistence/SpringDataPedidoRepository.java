package com.pedidos.sistema.pedido.infrastructure.adapters.out.persistence; // Paquete de persistencia.

import org.springframework.data.jpa.repository.JpaRepository; // CRUD de Spring Data.
import java.util.List; // Listas.

/** Repositorio Spring Data; las consultas se derivan del nombre del método. */
public interface SpringDataPedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findAllByOrderByFechaDesc(); // Todos, más recientes primero.

    List<PedidoEntity> findByEstadoIgnoreCaseOrderByFechaDesc(String estado); // Por estado, más recientes primero.
}
