package com.pedidos.sistema.pedido.application.service; // Paquete del servicio de pedidos.

import com.pedidos.sistema.pedido.domain.model.Pedido; // Modelo de dominio.
import com.pedidos.sistema.pedido.domain.ports.in.PedidoUseCase; // Puerto de entrada.
import com.pedidos.sistema.pedido.domain.ports.out.PedidoRepositoryPort; // Puerto de salida.
import org.springframework.stereotype.Service; // Servicio administrado por Spring.
import java.util.List; // Listas.

/** Servicio de aplicación: implementa las consultas de pedidos. */
@Service
public class PedidoService implements PedidoUseCase {

    private final PedidoRepositoryPort repositoryPort; // Dependencia hacia el puerto de persistencia.

    public PedidoService(PedidoRepositoryPort repositoryPort) { // Inyección por constructor.
        this.repositoryPort = repositoryPort;
    }

    @Override
    public List<Pedido> listarPedidos() { // Caso de uso: listar todos.
        return repositoryPort.listarTodos(); // Delega al puerto de salida.
    }

    @Override
    public List<Pedido> listarPedidosPorEstado(String estado) { // Caso de uso: filtrar por estado.
        if (estado == null || estado.isBlank()) { // Sin filtro válido...
            return repositoryPort.listarTodos(); // ...se devuelven todos.
        }
        return repositoryPort.listarPorEstado(estado.trim()); // Con filtro, consulta por estado.
    }
}
