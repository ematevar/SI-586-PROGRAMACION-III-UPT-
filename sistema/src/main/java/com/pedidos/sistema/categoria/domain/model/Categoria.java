package com.pedidos.sistema.categoria.domain.model; // Paquete del modelo de dominio.

/**
 * Objeto de dominio que representa una categoría de productos.
 * No depende de JPA ni de Spring: es Java puro (núcleo del hexágono).
 */
public class Categoria { // Define el objeto de dominio Categoría.

    private Long id; // Identificador de la categoría.
    private String nombre; // Nombre de la categoría.
    private String descripcion; // Descripción de la categoría.

    public Categoria() {} // Constructor vacío: permite crear el objeto sin datos (lo usa el formulario web).

    public Categoria(Long id, String nombre, String descripcion) { // Constructor con todos los campos.
        this.id = id; // Asigna el identificador recibido.
        this.nombre = nombre; // Asigna el nombre recibido.
        this.descripcion = descripcion; // Asigna la descripción recibida.
    }

    public Long getId() { return id; } // Devuelve el identificador.
    public void setId(Long id) { this.id = id; } // Actualiza el identificador.
    public String getNombre() { return nombre; } // Devuelve el nombre.
    public void setNombre(String nombre) { this.nombre = nombre; } // Actualiza el nombre.
    public String getDescripcion() { return descripcion; } // Devuelve la descripción.
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; } // Actualiza la descripción.
}
