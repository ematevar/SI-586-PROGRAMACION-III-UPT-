package com.pedidos.sistema.pedido.infrastructure.adapters.out.persistence; // Paquete de persistencia.

import jakarta.persistence.*; // Anotaciones JPA.
import lombok.Data; // Getters, setters, equals, hashCode, toString.
import java.math.BigDecimal; // Importe.
import java.time.LocalDateTime; // Fecha.

/** Entidad JPA que lee la tabla pedido (el módulo es de consulta, no de creación). */
@Entity
@Table(name = "pedido") // Tabla creada en el script SQL.
@Data
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Columna id.

    @Column(name = "cliente_id")
    private Long clienteId; // Columna cliente_id (usuario que compra).

    private LocalDateTime fecha; // Columna fecha.

    private BigDecimal total; // Columna total.

    private String estado; // Columna estado.
}
