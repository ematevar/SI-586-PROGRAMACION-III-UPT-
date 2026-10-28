package com.empresa.pedidos.infrastructure.persistence.entity;


 import jakarta.persistence.*;
 import jakarta.validation.constraints.Email;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.Size;


 @Entity
 @Table(name = "clientes_app")
 public class Cliente {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;


     @NotBlank
     @Size(max = 150)
     private String nombre;


     @NotBlank
     @Size(max = 20)
     @Column(unique = true)
     private String documento;


     @Size(max = 200)
     private String direccion;


     @Email
     @Size(max = 150)
     private String correo;


     public Long getId() { return id; }
     public String getNombre() { return nombre; }
     public void setNombre(String nombre) { this.nombre = nombre; }
     public String getDocumento() { return documento; }
     public void setDocumento(String documento) { this.documento = documento; }
     public String getDireccion() { return direccion; }
     public void setDireccion(String direccion) { this.direccion = direccion; }
     public String getCorreo() { return correo; }
     public void setCorreo(String correo) { this.correo = correo; }
 }
