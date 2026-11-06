package com.empresa.documentos.auth2fa.application.port.out;

import com.empresa.documentos.auth2fa.domain.model.IntentoLogin;
import com.empresa.documentos.auth2fa.domain.model.OtpVerificacion;
import com.empresa.documentos.auth2fa.domain.model.TokenSesion;
import com.empresa.documentos.auth2fa.domain.model.Usuario;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<Usuario> buscarPorUsername(String username);
    void actualizarUsuario(Usuario usuario);
    void registrarIntentoLogin(IntentoLogin intento);
    void invalidarOtpsAnteriores(Long usuarioId);
    void guardarOtp(OtpVerificacion otp);
    Optional<OtpVerificacion> buscarUltimoOtpPorUsuario(Long usuarioId);
    void actualizarOtp(OtpVerificacion otp);
    void marcarOtpUsado(Long otpId);
    void guardarToken(TokenSesion token);
}
