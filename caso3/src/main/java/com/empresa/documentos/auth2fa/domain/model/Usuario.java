package com.empresa.documentos.auth2fa.domain.model;

// Dominio puro: sin dependencias de Spring ni de JPA
public class Usuario {
    private Long id;
    private String username;
    private String passwordHash;
    private String email;
    private String telefono;
    private String canalPreferido;
    private int intentosFallidos;
    private String estado;

    public Usuario() {}

    public Usuario(Long id, String username, String passwordHash, String email,
                   String telefono, String canalPreferido, int intentosFallidos, String estado) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.telefono = telefono;
        this.canalPreferido = canalPreferido;
        this.intentosFallidos = intentosFallidos;
        this.estado = estado;
    }

    public boolean estaActivo() {
        return "ACTIVO".equalsIgnoreCase(this.estado);
    }

    public void registrarFalloLogin(IntentoLogin intento) {
        this.intentosFallidos++;
        if (intento.debeBloquearUsuario(this.intentosFallidos)) {
            this.estado = "BLOQUEADO";
        }
    }

    public void resetearIntentos() {
        this.intentosFallidos = 0;
    }

    public boolean prefiereSms() {
        return "SMS".equalsIgnoreCase(this.canalPreferido);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCanalPreferido() { return canalPreferido; }
    public void setCanalPreferido(String canalPreferido) { this.canalPreferido = canalPreferido; }
    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
