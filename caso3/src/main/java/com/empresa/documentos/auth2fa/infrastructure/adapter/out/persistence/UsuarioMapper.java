package com.empresa.documentos.auth2fa.infrastructure.adapter.out.persistence;

import com.empresa.documentos.auth2fa.domain.model.OtpVerificacion;
import com.empresa.documentos.auth2fa.domain.model.TokenSesion;
import com.empresa.documentos.auth2fa.domain.model.Usuario;

public class UsuarioMapper {

    public static Usuario toDomain(UsuarioEntity e) {
        return new Usuario(
                e.getId(),
                e.getUsername(),
                e.getPassword(),
                e.getEmail(),
                e.getTelefono(),
                e.getCanalPreferido() != null ? e.getCanalPreferido() : "EMAIL",
                e.getIntentosFallidos() != null ? e.getIntentosFallidos() : 0,
                e.getEstado()
        );
    }

    public static UsuarioEntity toEntity(Usuario d) {
        UsuarioEntity e = new UsuarioEntity();
        e.setId(d.getId());
        e.setUsername(d.getUsername());
        e.setPassword(d.getPasswordHash());
        e.setEmail(d.getEmail());
        e.setTelefono(d.getTelefono());
        e.setCanalPreferido(d.getCanalPreferido());
        e.setIntentosFallidos(d.getIntentosFallidos());
        e.setEstado(d.getEstado());
        return e;
    }

    public static OtpVerificacion toDomain(OtpVerificacionEntity e) {
        return new OtpVerificacion(
                e.getId(),
                e.getUsuarioId(),
                e.getCodigo(),
                e.getFechaExpiracion(),
                Boolean.TRUE.equals(e.getUsado()),
                e.getIntentosFallidos() != null ? e.getIntentosFallidos() : 0
        );
    }

    public static OtpVerificacionEntity toEntity(OtpVerificacion d) {
        OtpVerificacionEntity e = new OtpVerificacionEntity();
        e.setId(d.getId());
        e.setUsuarioId(d.getUsuarioId());
        e.setCodigo(d.getCodigo());
        e.setFechaExpiracion(d.getFechaExpiracion());
        e.setUsado(d.isUsado());
        e.setIntentosFallidos(d.getIntentosFallidos());
        return e;
    }

    public static TokenSesionEntity toEntity(TokenSesion d) {
        TokenSesionEntity e = new TokenSesionEntity();
        e.setId(d.getId());
        e.setUsuarioId(d.getUsuarioId());
        e.setToken(d.getToken());
        e.setFechaExpiracion(d.getFechaExpiracion());
        return e;
    }
}
