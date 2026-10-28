package com.empresa.pedidos.infrastructure.persistence.entity;


 import jakarta.persistence.*;


 @Entity
 @Table(name = "usuarios_app")
 public class UsuarioEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;


     @Column(nullable = false, unique = true, length = 80)
     private String username;


     @Column(nullable = false, length = 100)
     private String passwordHash;


     @Column(nullable = false, length = 30)
     private String rol = "EMPLEADO";


     @Column(nullable = false)
     private boolean activo = true;


     public Long getId() { return id; }
     public String getUsername() { return username; }
     public String getPasswordHash() { return passwordHash; }
     public String getRol() { return rol; }
     public boolean isActivo() { return activo; }
     public void setId(Long id) { this.id = id; }
     public void setUsername(String username) { this.username = username; }
     public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
     public void setRol(String rol) { this.rol = rol; }
     public void setActivo(boolean activo) { this.activo = activo; }
 }
