package com.empresa.pedidos.application.usecase;
import com.empresa.pedidos.domain.model.Cliente;
import com.empresa.pedidos.domain.port.out.ClientePort;
import java.util.List;
public class ListarClientes {
private final ClientePort port;
public ListarClientes(ClientePort port){this.port=port;}
public List<Cliente> ejecutar(){return port.findAll();}
}
