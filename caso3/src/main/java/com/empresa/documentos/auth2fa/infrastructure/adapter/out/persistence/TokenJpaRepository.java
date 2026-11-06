package com.empresa.documentos.auth2fa.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<TokenSesionEntity, Long> {
}
