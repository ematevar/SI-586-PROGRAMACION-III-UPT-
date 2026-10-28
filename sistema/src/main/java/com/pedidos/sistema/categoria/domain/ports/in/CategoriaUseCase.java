package com.pedidos.sistema.categoria.domain.ports.in; // Paquete de puertos de entrada.

import com.pedidos.sistema.categoria.domain.model.Categoria; // Modelo de dominio.
import java.util.List; // Permite devolver listas.
import java.util.Optional; // Resultado que puede no existir.

/** Puerto de entrada: operaciones que la aplicación ofrece sobre categorías. */
public interface CategoriaUseCase {
    Categoria crearCategoria(Categoria categoria); // Creación.
    Optional<Categoria> obtenerCategoriaPorId(Long id); // Búsqueda por ID.
    List<Categoria> obtenerTodas(); // Listado completo.
    Optional<Categoria> actualizarCategoria(Long id, Categoria categoria); // Actualiza si existe.
    boolean eliminarCategoria(Long id); // Indica si se eliminó la categoría.
}
