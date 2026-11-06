package com.empresa.documentos.domain.model;
import java.time.LocalDateTime;
public record Envio(Long id, Long empleadoId, String empleado, String codigo, String correo, String tipo, String plantilla, String contenido, String token, LocalDateTime fechaEnvio, LocalDateTime fechaApertura, String correoEstado, String correoError) {
 public String estado(){return fechaApertura==null?"PENDIENTE":"VISTO";}
}
