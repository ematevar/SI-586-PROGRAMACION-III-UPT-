package com.pedidos.sistema.pedido.domain.ports.in; // Paquete de puertos de entrada.

import com.pedidos.sistema.pedido.domain.model.Pedido; // Modelo de dominio.
import java.util.List; // Listas.

/** Puerto de entrada: casos de uso de consulta de pedidos (módulo solo de lectura). */
public interface PedidoUseCase {
    List<Pedido> listarPedidos(); // Todos los pedidos, del más reciente al más antiguo.
    List<Pedido> listarPedidosPorEstado(String estado); // Ampliación opcional: filtro por estado.
}
