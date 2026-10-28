package com.empresa.pedidos.infrastructure.persistence.adapter;
import com.empresa.pedidos.domain.model.Cliente;
import com.empresa.pedidos.domain.port.out.ClientePort;
import com.empresa.pedidos.infrastructure.persistence.repository.ClienteJpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public class ClienteJpaAdapter implements ClientePort {
private final ClienteJpaRepository repository;
public ClienteJpaAdapter(ClienteJpaRepository repository){this.repository=repository;}
public java.util.List<Cliente> findAll(){return repository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC,"id")).stream().map(e->{var u=new Cliente();u.setId(e.getId());u.setNombre(e.getNombre());u.setDocumento(e.getDocumento());u.setDireccion(e.getDireccion());u.setCorreo(e.getCorreo());return u;}).toList();}
public boolean existsByDocumento(String documento){return repository.existsByDocumento(documento);}
public void save(Cliente value){
var entity=new com.empresa.pedidos.infrastructure.persistence.entity.Cliente();
entity.setNombre(value.getNombre());
entity.setDocumento(value.getDocumento());
entity.setDireccion(value.getDireccion());
entity.setCorreo(value.getCorreo());
repository.saveAndFlush(entity);
}
}
