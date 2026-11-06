package com.empresa.documentos.auth2fa.domain.model;

import java.time.LocalDateTime;

public class TokenSesion {
    private Long id;
    private Long usuarioId;
    private String token;
    private LocalDateTime fechaExpiracion;

    public TokenSesion() {}

    public TokenSesion(Long id, Long usuarioId, String token, LocalDateTime fechaExpiracion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.token = token;
        this.fechaExpiracion = fechaExpiracion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
}
