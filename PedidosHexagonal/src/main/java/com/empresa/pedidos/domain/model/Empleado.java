package com.empresa.pedidos.domain.model;
public class Empleado {
private Long id;
public Long getId(){return id;}
public void setId(Long id){this.id=id;}
private String nombres;
public String getNombres(){return nombres;}
public void setNombres(String value){this.nombres=value;}
private String apellidos;
public String getApellidos(){return apellidos;}
public void setApellidos(String value){this.apellidos=value;}
private String documento;
public String getDocumento(){return documento;}
public void setDocumento(String value){this.documento=value;}
private String correo;
public String getCorreo(){return correo;}
public void setCorreo(String value){this.correo=value;}
}
