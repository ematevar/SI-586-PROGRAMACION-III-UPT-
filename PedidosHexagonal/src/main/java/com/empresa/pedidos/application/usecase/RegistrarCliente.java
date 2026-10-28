package com.empresa.pedidos.application.usecase;
import com.empresa.pedidos.domain.model.Cliente;
import com.empresa.pedidos.domain.port.out.ClientePort;
public class RegistrarCliente {
private final ClientePort port;
public RegistrarCliente(ClientePort port){this.port=port;}
public void ejecutar(Cliente value){
if(value.getDocumento()==null || value.getDocumento().isBlank()) throw new IllegalArgumentException("El documento es obligatorio.");
value.setDocumento(value.getDocumento().trim());
if(port.existsByDocumento(value.getDocumento())) throw new IllegalArgumentException("El documento ya se encuentra registrado.");
port.save(value);
}
}
