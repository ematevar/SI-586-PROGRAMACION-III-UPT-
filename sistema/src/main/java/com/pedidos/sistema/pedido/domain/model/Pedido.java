package com.pedidos.sistema.pedido.domain.model; // Paquete del modelo de dominio de pedidos.

import java.math.BigDecimal; // Tipo exacto para importes monetarios.
import java.time.LocalDateTime; // Fecha y hora de creación.

/** Objeto de dominio que representa un pedido (cabecera). Java puro, sin JPA. */
public class Pedido {

    private Long id; // Identificador del pedido.
    private Long clienteId; // ID del usuario que compra.
    private LocalDateTime fecha; // Fecha de creación.
    private BigDecimal total; // Importe total.
    private String estado; // PENDIENTE, APROBADO, DESPACHADO...

    public Pedido() {} // Constructor vacío.

    public Pedido(Long id, Long clienteId, LocalDateTime fecha, BigDecimal total, String estado) { // Todos los campos.
        this.id = id; // Asigna el ID.
        this.clienteId = clienteId; // Asigna el cliente.
        this.fecha = fecha; // Asigna la fecha.
        this.total = total; // Asigna el total.
        this.estado = estado; // Asigna el estado.
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
