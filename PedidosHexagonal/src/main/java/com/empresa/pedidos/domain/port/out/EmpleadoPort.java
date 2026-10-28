package com.empresa.pedidos.domain.port.out;
import com.empresa.pedidos.domain.model.Empleado;
public interface EmpleadoPort { java.util.List<Empleado> findAll(); boolean existsByDocumento(String documento); void save(Empleado value); }
