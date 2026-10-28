package com.pedidos.sistema.categoria.application.service; // Paquete del servicio.

import com.pedidos.sistema.categoria.domain.model.Categoria; // Modelo de dominio.
import com.pedidos.sistema.categoria.domain.ports.in.CategoriaUseCase; // Puerto de entrada.
import com.pedidos.sistema.categoria.domain.ports.out.CategoriaRepositoryPort; // Puerto de salida.
import org.springframework.stereotype.Service; // Registra el servicio en Spring.
import java.util.List; // Colección.
import java.util.Optional; // Resultado opcional.

/** Servicio de aplicación: implementa los casos de uso coordinando el puerto de salida. */
@Service // Componente de servicio administrado por Spring.
public class CategoriaService implements CategoriaUseCase {

    private final CategoriaRepositoryPort repositoryPort; // Dependencia hacia el puerto de persistencia.

    public CategoriaService(CategoriaRepositoryPort repositoryPort) { // Inyección por constructor.
        this.repositoryPort = repositoryPort; // Guarda la implementación que Spring inyecta.
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) { // Caso de uso: crear.
        return repositoryPort.guardar(categoria); // Delega el guardado al puerto de salida.
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorId(Long id) { // Caso de uso: buscar por ID.
        return repositoryPort.buscarPorId(id); // Devuelve el resultado del repositorio.
    }

    @Override
    public List<Categoria> obtenerTodas() { // Caso de uso: listar.
        return repositoryPort.listarTodas(); // Delega la consulta al adaptador.
    }

    @Override
    public Optional<Categoria> actualizarCategoria(Long id, Categoria categoria) { // Caso de uso: actualizar.
        return repositoryPort.buscarPorId(id).map(cat -> { // Solo continúa si la categoría existe.
            cat.setNombre(categoria.getNombre()); // Copia el nuevo nombre.
            cat.setDescripcion(categoria.getDescripcion()); // Copia la nueva descripción.
            return repositoryPort.actualizar(cat); // Persiste y devuelve la categoría actualizada.
        }); // Si no existe, devuelve Optional vacío.
    }

    @Override
    public boolean eliminarCategoria(Long id) { // Caso de uso: eliminar.
        if (repositoryPort.buscarPorId(id).isPresent()) { // Comprueba que el registro exista.
            return repositoryPort.eliminar(id); // Solicita la eliminación y devuelve el resultado.
        }
        return false; // No se eliminó porque no existía.
    }
}
