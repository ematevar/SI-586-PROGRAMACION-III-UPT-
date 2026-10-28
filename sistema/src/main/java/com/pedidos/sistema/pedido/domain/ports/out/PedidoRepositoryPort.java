package com.pedidos.sistema.pedido.domain.ports.out; // Paquete de puertos de salida.

import com.pedidos.sistema.pedido.domain.model.Pedido; // Modelo de dominio.
import java.util.List; // Listas.

/** Puerto de salida: lectura de pedidos desde la persistencia. */
public interface PedidoRepositoryPort {
    List<Pedido> listarTodos(); // Recupera todos los pedidos.
    List<Pedido> listarPorEstado(String estado); // Recupera los pedidos de un estado.
}
