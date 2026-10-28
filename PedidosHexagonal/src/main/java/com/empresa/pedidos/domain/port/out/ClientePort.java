package com.empresa.pedidos.domain.port.out;
import com.empresa.pedidos.domain.model.Cliente;
public interface ClientePort { boolean existsByDocumento(String documento); void save(Cliente value); }
