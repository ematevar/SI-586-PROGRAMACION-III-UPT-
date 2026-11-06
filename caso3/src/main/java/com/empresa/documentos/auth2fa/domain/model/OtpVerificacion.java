package com.empresa.documentos.auth2fa.domain.model;

import java.time.LocalDateTime;

public class OtpVerificacion {
    private static final int MAX_INTENTOS_OTP = 3;

    private Long id;
    private Long usuarioId;
    private String codigo;
    private LocalDateTime fechaExpiracion;
    private boolean usado;
    private int intentosFallidos;

    public OtpVerificacion() {}

    public OtpVerificacion(Long id, Long usuarioId, String codigo,
                           LocalDateTime fechaExpiracion, boolean usado, int intentosFallidos) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.codigo = codigo;
        this.fechaExpiracion = fechaExpiracion;
        this.usado = usado;
        this.intentosFallidos = intentosFallidos;
    }

    public boolean estaExpirado() {
        return !LocalDateTime.now().isBefore(fechaExpiracion);
    }

    public boolean esValido() {
        return !usado && !estaExpirado() && intentosFallidos < MAX_INTENTOS_OTP;
    }

    // Reto Ej. 4: invalida el OTP automaticamente tras 3 codigos incorrectos
    public void registrarIntentoFallido() {
        this.intentosFallidos++;
        if (this.intentosFallidos >= MAX_INTENTOS_OTP) {
            this.usado = true;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
    public boolean isUsado() { return usado; }
    public void setUsado(boolean usado) { this.usado = usado; }
    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }
}
