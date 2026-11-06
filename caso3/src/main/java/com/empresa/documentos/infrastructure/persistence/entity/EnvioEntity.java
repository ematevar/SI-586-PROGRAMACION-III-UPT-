package com.empresa.documentos.infrastructure.persistence.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="envios_caso3")
public class EnvioEntity {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 public Long empleadoId;
 public String empleado; public String codigo; public String correo; public String tipo; public String plantilla;
 @Column(length=10000) public String contenido;
 @Column(unique=true,nullable=false) public String token;
 public LocalDateTime fechaEnvio; public LocalDateTime fechaApertura;
 public String correoEstado;
 @Column(length=500) public String correoError;
 @Version public Long version;
}
