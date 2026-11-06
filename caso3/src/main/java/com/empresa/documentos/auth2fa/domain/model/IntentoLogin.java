package com.empresa.documentos.auth2fa.domain.model;

import java.time.LocalDateTime;

public class IntentoLogin {
    private static final int MAX_INTENTOS_PERMITIDOS = 3;

    private Long id;
    private Long usuarioId;
    private LocalDateTime fechaIntento;
    private boolean exitoso;

    public IntentoLogin() {}

    public IntentoLogin(Long id, Long usuarioId, LocalDateTime fechaIntento, boolean exitoso) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fechaIntento = fechaIntento;
        this.exitoso = exitoso;
    }

    // Regla de dominio: bloquea al llegar a 3 intentos fallidos
    public boolean debeBloquearUsuario(int intentosAcumulados) {
        return !this.exitoso && intentosAcumulados >= MAX_INTENTOS_PERMITIDOS;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public LocalDateTime getFechaIntento() { return fechaIntento; }
    public void setFechaIntento(LocalDateTime fechaIntento) { this.fechaIntento = fechaIntento; }
    public boolean isExitoso() { return exitoso; }
    public void setExitoso(boolean exitoso) { this.exitoso = exitoso; }
}
