package com.empresa.pedidos.infrastructure.persistence.repository;


 import com.empresa.pedidos.infrastructure.persistence.entity.Empleado;
 import org.springframework.data.jpa.repository.JpaRepository;


 public interface EmpleadoJpaRepository extends JpaRepository<Empleado, Long> {
     boolean existsByDocumento(String documento);
 }
