package com.empresa.pedidos.domain.model;


  public class Usuario {
      private Long id;
      private String username;
      private String passwordHash;
      private String rol;
      private boolean activo;


      public Long getId() { return id; }
      public void setId(Long id) { this.id = id; }
      public String getUsername() { return username; }
      public void setUsername(String username) { this.username = username; }
      public String getPasswordHash() { return passwordHash; }
      public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
      public String getRol() { return rol; }
      public void setRol(String rol) { this.rol = rol; }
      public boolean isActivo() { return activo; }
      public void setActivo(boolean activo) { this.activo = activo; }
  }
