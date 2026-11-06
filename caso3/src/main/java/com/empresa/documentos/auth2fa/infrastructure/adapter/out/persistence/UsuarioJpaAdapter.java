package com.empresa.documentos.auth2fa.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;
import com.empresa.documentos.auth2fa.application.port.out.UserRepositoryPort;
import com.empresa.documentos.auth2fa.domain.model.IntentoLogin;
import com.empresa.documentos.auth2fa.domain.model.OtpVerificacion;
import com.empresa.documentos.auth2fa.domain.model.TokenSesion;
import com.empresa.documentos.auth2fa.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioJpaAdapter implements UserRepositoryPort {

    private final UsuarioJpaRepository usuarioRepo;
    private final OtpJpaRepository otpRepo;
    private final TokenJpaRepository tokenRepo;
    private final IntentoLoginJpaRepository intentoRepo;

    public UsuarioJpaAdapter(UsuarioJpaRepository usuarioRepo,
                             OtpJpaRepository otpRepo,
                             TokenJpaRepository tokenRepo,
                             IntentoLoginJpaRepository intentoRepo) {
        this.usuarioRepo = usuarioRepo;
        this.otpRepo = otpRepo;
        this.tokenRepo = tokenRepo;
        this.intentoRepo = intentoRepo;
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepo.findByUsername(username).map(UsuarioMapper::toDomain);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        usuarioRepo.save(UsuarioMapper.toEntity(usuario));
    }

    @Override
    public void registrarIntentoLogin(IntentoLogin intento) {
        IntentoLoginEntity e = new IntentoLoginEntity();
        e.setUsuarioId(intento.getUsuarioId());
        e.setFechaIntento(intento.getFechaIntento());
        e.setExitoso(intento.isExitoso());
        intentoRepo.save(e);
    }

    @Override
    public void invalidarOtpsAnteriores(Long usuarioId) {
        List<OtpVerificacionEntity> activos = otpRepo.findByUsuarioIdAndUsadoFalse(usuarioId);
        for (OtpVerificacionEntity otp : activos) {
            otp.setUsado(true);
        }
        otpRepo.saveAll(activos);
    }

    @Override
    public void guardarOtp(OtpVerificacion otp) {
        otpRepo.save(UsuarioMapper.toEntity(otp));
    }

    @Override
    public Optional<OtpVerificacion> buscarUltimoOtpPorUsuario(Long usuarioId) {
        return otpRepo.findTopByUsuarioIdOrderByIdDesc(usuarioId).map(UsuarioMapper::toDomain);
    }

    @Override
    public void actualizarOtp(OtpVerificacion otp) {
        otpRepo.save(UsuarioMapper.toEntity(otp));
    }

    @Override
    public void marcarOtpUsado(Long otpId) {
        otpRepo.findById(otpId).ifPresent(entity -> {
            entity.setUsado(true);
            otpRepo.save(entity);
        });
    }

    @Override
    public void guardarToken(TokenSesion token) {
        tokenRepo.save(UsuarioMapper.toEntity(token));
    }
}
