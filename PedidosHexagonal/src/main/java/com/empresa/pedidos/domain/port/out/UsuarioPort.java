package com.empresa.pedidos.domain.port.out;
import com.empresa.pedidos.domain.model.Usuario;
import java.util.Optional;
public interface UsuarioPort {Optional<Usuario> findByUsername(String username); void save(Usuario usuario);}
