package com.pedidos.sistema.categoria.domain.ports.out; // Paquete de puertos de salida.

import com.pedidos.sistema.categoria.domain.model.Categoria; // Modelo.
import java.util.List; // Colección de categorías.
import java.util.Optional; // Búsquedas posiblemente vacías.

/** Puerto de salida: lo que el dominio necesita de la persistencia. */
public interface CategoriaRepositoryPort {
    Categoria guardar(Categoria categoria); // Guarda una categoría nueva.
    Optional<Categoria> buscarPorId(Long id); // Busca por identificador.
    List<Categoria> listarTodas(); // Recupera todos los registros.
    Categoria actualizar(Categoria categoria); // Guarda los cambios.
    boolean eliminar(Long id); // Elimina por identificador.
}
