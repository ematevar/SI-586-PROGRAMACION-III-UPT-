package com.empresa.pedidos.infrastructure.config;
import com.empresa.pedidos.domain.port.out.*;
import com.empresa.pedidos.application.usecase.*;
import org.springframework.context.annotation.*;
@Configuration
public class CasosUsoConfig {
@Bean RegistrarEmpleado registrarEmpleado(EmpleadoPort port){return new RegistrarEmpleado(port);}
@Bean RegistrarCliente registrarCliente(ClientePort port){return new RegistrarCliente(port);}
}
