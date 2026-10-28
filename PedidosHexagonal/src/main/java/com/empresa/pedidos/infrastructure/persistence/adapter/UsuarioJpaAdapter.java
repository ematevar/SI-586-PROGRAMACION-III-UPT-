package com.empresa.pedidos.infrastructure.persistence.adapter;
import com.empresa.pedidos.domain.model.Usuario;
import com.empresa.pedidos.domain.port.out.UsuarioPort;
import com.empresa.pedidos.infrastructure.persistence.repository.UsuarioJpaRepository;
import com.empresa.pedidos.infrastructure.persistence.entity.UsuarioEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;
@Repository
public class UsuarioJpaAdapter implements UsuarioPort {
private final UsuarioJpaRepository repository;
public UsuarioJpaAdapter(UsuarioJpaRepository repository){this.repository=repository;}
public Optional<Usuario> findByUsername(String name){return repository.findByUsername(name).map(e->{var u=new Usuario();u.setId(e.getId());u.setUsername(e.getUsername());u.setPasswordHash(e.getPasswordHash());u.setRol(e.getRol());u.setActivo(e.isActivo());return u;});}
public void save(Usuario u){var e=new UsuarioEntity();e.setId(u.getId());e.setUsername(u.getUsername());e.setPasswordHash(u.getPasswordHash());e.setRol(u.getRol());e.setActivo(u.isActivo());repository.save(e);}
}
