package com.empresa.pedidos.domain.port.out;
import com.empresa.pedidos.domain.model.Cliente;
public interface ClientePort { java.util.List<Cliente> findAll(); boolean existsByDocumento(String documento); void save(Cliente value); }
