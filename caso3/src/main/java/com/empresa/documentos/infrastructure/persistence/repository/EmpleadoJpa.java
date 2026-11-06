package com.empresa.documentos.infrastructure.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.documentos.infrastructure.persistence.entity.*;
public interface EmpleadoJpa extends JpaRepository<EmpleadoEntity,Long>{}
