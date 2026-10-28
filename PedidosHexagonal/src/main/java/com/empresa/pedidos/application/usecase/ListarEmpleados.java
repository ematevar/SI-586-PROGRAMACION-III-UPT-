package com.empresa.pedidos.application.usecase;
import com.empresa.pedidos.domain.model.Empleado;
import com.empresa.pedidos.domain.port.out.EmpleadoPort;
import java.util.List;
public class ListarEmpleados {
private final EmpleadoPort port;
public ListarEmpleados(EmpleadoPort port){this.port=port;}
public List<Empleado> ejecutar(){return port.findAll();}
}
