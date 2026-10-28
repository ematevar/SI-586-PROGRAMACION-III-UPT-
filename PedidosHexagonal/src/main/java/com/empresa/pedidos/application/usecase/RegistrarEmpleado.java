package com.empresa.pedidos.application.usecase;
import com.empresa.pedidos.domain.model.Empleado;
import com.empresa.pedidos.domain.port.out.EmpleadoPort;
public class RegistrarEmpleado {
private final EmpleadoPort port;
public RegistrarEmpleado(EmpleadoPort port){this.port=port;}
public void ejecutar(Empleado value){
if(value.getDocumento()==null || value.getDocumento().isBlank()) throw new IllegalArgumentException("El documento es obligatorio.");
value.setDocumento(value.getDocumento().trim());
if(port.existsByDocumento(value.getDocumento())) throw new IllegalArgumentException("El documento ya se encuentra registrado.");
port.save(value);
}
}
