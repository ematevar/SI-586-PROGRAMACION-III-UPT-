package com.empresa.pedidos.infrastructure.persistence.repository;


 import com.empresa.pedidos.infrastructure.persistence.entity.UsuarioEntity;
 import java.util.Optional;
 import org.springframework.data.jpa.repository.JpaRepository;






 public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
     Optional<UsuarioEntity> findByUsername(String username);
 }
