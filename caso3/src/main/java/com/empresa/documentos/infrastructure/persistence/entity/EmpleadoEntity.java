package com.empresa.documentos.infrastructure.persistence.entity;
import jakarta.persistence.*;
@Entity @Table(name="empleados_caso3")
public class EmpleadoEntity {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(unique=true,nullable=false) public String codigo;
 @Column(nullable=false) public String nombre;
 @Column(nullable=false) public String correo;
 public String cargo; public String ingreso;
}
