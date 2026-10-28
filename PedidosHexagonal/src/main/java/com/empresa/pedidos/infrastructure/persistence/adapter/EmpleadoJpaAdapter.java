package com.empresa.pedidos.infrastructure.persistence.adapter;
import com.empresa.pedidos.domain.model.Empleado;
import com.empresa.pedidos.domain.port.out.EmpleadoPort;
import com.empresa.pedidos.infrastructure.persistence.repository.EmpleadoJpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public class EmpleadoJpaAdapter implements EmpleadoPort {
private final EmpleadoJpaRepository repository;
public EmpleadoJpaAdapter(EmpleadoJpaRepository repository){this.repository=repository;}
public boolean existsByDocumento(String documento){return repository.existsByDocumento(documento);}
public void save(Empleado value){
var entity=new com.empresa.pedidos.infrastructure.persistence.entity.Empleado();
entity.setNombres(value.getNombres());
entity.setApellidos(value.getApellidos());
entity.setDocumento(value.getDocumento());
entity.setCorreo(value.getCorreo());
repository.saveAndFlush(entity);
}
}
