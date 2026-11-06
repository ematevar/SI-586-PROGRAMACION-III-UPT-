package com.empresa.documentos.infrastructure.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.documentos.infrastructure.persistence.entity.*;
import java.util.Optional;
public interface EnvioJpa extends JpaRepository<EnvioEntity,Long>{Optional<EnvioEntity> findByToken(String token);}
