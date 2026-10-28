package com.pedidos.sistema.categoria.infrastructure.adapters.out.persistence; // Paquete de persistencia.

import org.springframework.data.jpa.repository.JpaRepository; // Operaciones CRUD de Spring Data.

/**
 * Hereda guardar, buscar por ID, listar y eliminar sin escribir SQL manual.
 * CategoriaEntity es el tipo de entidad y Long el tipo de su clave primaria.
 */
public interface SpringDataCategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
}
