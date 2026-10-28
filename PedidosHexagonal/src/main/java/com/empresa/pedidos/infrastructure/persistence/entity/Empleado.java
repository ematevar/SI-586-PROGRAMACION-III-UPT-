package com.empresa.pedidos.infrastructure.persistence.entity;


 import jakarta.persistence.*;
 import jakarta.validation.constraints.Email;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.Size;


 @Entity
 @Table(name = "empleados_app")
 public class Empleado {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;


     @NotBlank
     @Size(max = 100)
     private String nombres;


     @NotBlank
     @Size(max = 100)
     private String apellidos;


     @NotBlank
     @Size(max = 20)
     @Column(unique = true)
     private String documento;





     @Email
     @Size(max = 150)
     private String correo;


     public Long getId() { return id; }
     public String getNombres() { return nombres; }
     public void setNombres(String nombres) { this.nombres = nombres; }
     public String getApellidos() { return apellidos; }
     public void setApellidos(String apellidos) { this.apellidos = apellidos; }
     public String getDocumento() { return documento; }
     public void setDocumento(String documento) { this.documento = documento; }
     public String getCorreo() { return correo; }
     public void setCorreo(String correo) { this.correo = correo; }
 }
