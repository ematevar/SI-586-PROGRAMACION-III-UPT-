package com.empresa.documentos.auth2fa.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OtpJpaRepository extends JpaRepository<OtpVerificacionEntity, Long> {
    Optional<OtpVerificacionEntity> findTopByUsuarioIdOrderByIdDesc(Long usuarioId);
    List<OtpVerificacionEntity> findByUsuarioIdAndUsadoFalse(Long usuarioId);
}
