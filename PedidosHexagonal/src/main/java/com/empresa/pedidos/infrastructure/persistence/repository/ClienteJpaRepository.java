package com.empresa.pedidos.infrastructure.persistence.repository;


 import com.empresa.pedidos.infrastructure.persistence.entity.Cliente;
 import org.springframework.data.jpa.repository.JpaRepository;


 public interface ClienteJpaRepository extends JpaRepository<Cliente, Long> {
     boolean existsByDocumento(String documento);
 }
