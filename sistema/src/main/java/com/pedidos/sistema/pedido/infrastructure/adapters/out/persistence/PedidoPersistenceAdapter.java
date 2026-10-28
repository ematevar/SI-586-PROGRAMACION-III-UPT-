package com.pedidos.sistema.pedido.infrastructure.adapters.out.persistence; // Paquete del adaptador.

import com.pedidos.sistema.pedido.domain.model.Pedido; // Modelo de dominio.
import com.pedidos.sistema.pedido.domain.ports.out.PedidoRepositoryPort; // Puerto que se implementa.
import org.springframework.stereotype.Component; // Registro en Spring.
import java.util.List; // Listas.
import java.util.stream.Collectors; // Recolector de streams.

/** Adaptador de salida: lee PedidoEntity y lo traduce a Pedido (dominio). */
@Component
public class PedidoPersistenceAdapter implements PedidoRepositoryPort {

    private final SpringDataPedidoRepository repository; // Repositorio JPA.

    public PedidoPersistenceAdapter(SpringDataPedidoRepository repository) { // Inyección por constructor.
        this.repository = repository;
    }

    @Override
    public List<Pedido> listarTodos() { // Todos los pedidos.
        return repository.findAllByOrderByFechaDesc().stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Pedido> listarPorEstado(String estado) { // Pedidos de un estado.
        return repository.findByEstadoIgnoreCaseOrderByFechaDesc(estado).stream()
                .map(this::toModel).collect(Collectors.toList());
    }

    private Pedido toModel(PedidoEntity e) { // Entidad JPA -> dominio.
        return new Pedido(e.getId(), e.getClienteId(), e.getFecha(), e.getTotal(), e.getEstado());
    }
}
