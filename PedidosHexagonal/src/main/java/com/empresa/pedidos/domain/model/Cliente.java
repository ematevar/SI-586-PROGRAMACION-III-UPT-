package com.empresa.pedidos.domain.model;
public class Cliente {
private Long id;
public Long getId(){return id;}
public void setId(Long id){this.id=id;}
private String nombre;
public String getNombre(){return nombre;}
public void setNombre(String value){this.nombre=value;}
private String documento;
public String getDocumento(){return documento;}
public void setDocumento(String value){this.documento=value;}
private String direccion;
public String getDireccion(){return direccion;}
public void setDireccion(String value){this.direccion=value;}
private String correo;
public String getCorreo(){return correo;}
public void setCorreo(String value){this.correo=value;}
}
