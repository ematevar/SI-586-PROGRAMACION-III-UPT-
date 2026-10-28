package com.pedidos.sistema.categoria.infrastructure.adapters.out.persistence; // Paquete del adaptador.

import com.pedidos.sistema.categoria.domain.model.Categoria; // Modelo del dominio.
import com.pedidos.sistema.categoria.domain.ports.out.CategoriaRepositoryPort; // Puerto que se implementa.
import org.springframework.dao.DataIntegrityViolationException; // Error de integridad (p. ej. FK con productos).
import org.springframework.stereotype.Component; // Registra el adaptador en Spring.
import java.util.List; // Colecciones.
import java.util.Optional; // Resultado opcional.
import java.util.stream.Collectors; // Recolecta elementos de un flujo.

/** Adaptador de salida: traduce entre el modelo de dominio y JPA. */
@Component // Spring detecta e inyecta el adaptador.
public class CategoriaPersistenceAdapter implements CategoriaRepositoryPort {

    private final SpringDataCategoriaRepository repository; // Repositorio JPA que ejecuta las operaciones.

    public CategoriaPersistenceAdapter(SpringDataCategoriaRepository repository) { // Constructor.
        this.repository = repository; // Recibe el repositorio por inyección de dependencias.
    }

    @Override
    public Categoria guardar(Categoria categoria) { // Recibe el modelo de dominio.
        return toModel(repository.save(toEntity(categoria))); // Convierte, guarda y convierte la respuesta.
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) { // Recibe el identificador.
        return repository.findById(id).map(this::toModel); // Busca y convierte si existe.
    }

    @Override
    public List<Categoria> listarTodas() { // Solicita todos los registros.
        return repository.findAll().stream().map(this::toModel).collect(Collectors.toList()); // Entidad -> modelo.
    }

    @Override
    public Categoria actualizar(Categoria categoria) { // Recibe el modelo modificado.
        return toModel(repository.save(toEntity(categoria))); // save actualiza si el ID ya existe.
    }

    @Override
    public boolean eliminar(Long id) { // Recibe el ID.
        try {
            repository.deleteById(id); // Elimina la fila con ese identificador.
            repository.flush(); // Fuerza el DELETE para detectar aquí errores de integridad.
            return true; // La eliminación terminó sin excepción.
        } catch (DataIntegrityViolationException e) { // Ej.: la categoría tiene productos asociados.
            return false; // Informa que no se pudo eliminar.
        }
    }

    private CategoriaEntity toEntity(Categoria categoria) { // Convierte del dominio a entidad JPA.
        CategoriaEntity entity = new CategoriaEntity(); // Crea una entidad nueva.
        entity.setId(categoria.getId()); // Copia el ID.
        entity.setNombre(categoria.getNombre()); // Copia el nombre.
        entity.setDescripcion(categoria.getDescripcion()); // Copia la descripción.
        return entity; // Entidad lista para persistir.
    }

    private Categoria toModel(CategoriaEntity entity) { // Convierte de entidad JPA a dominio.
        return new Categoria(entity.getId(), entity.getNombre(), entity.getDescripcion()); // Crea el modelo.
    }
}
