package com.pedidos.sistema.categoria.infrastructure.adapters.out.persistence; // Paquete de persistencia.

import jakarta.persistence.*; // Anotaciones JPA.
import lombok.Data; // Genera getters, setters, equals, hashCode y toString.

/** Entidad JPA: representación de persistencia de una categoría. */
@Entity // La clase se representa como una tabla.
@Table(name = "categoria") // Vincula la entidad con la tabla categoria.
@Data // Lombok genera los métodos comunes.
public class CategoriaEntity {

    @Id // Clave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El ID autoincremental lo genera MySQL.
    private Long id; // Columna identificadora.

    private String nombre; // Columna nombre.

    private String descripcion; // Columna descripción.
}
