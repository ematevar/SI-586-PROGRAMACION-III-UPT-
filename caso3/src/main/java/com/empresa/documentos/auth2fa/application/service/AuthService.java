package com.empresa.documentos.auth2fa.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.empresa.documentos.auth2fa.application.port.in.AuthPort;
import com.empresa.documentos.auth2fa.application.port.in.OtpPort;
import com.empresa.documentos.auth2fa.application.port.out.TokenPort;
import com.empresa.documentos.auth2fa.application.port.out.UserRepositoryPort;
import com.empresa.documentos.auth2fa.domain.exception.CredencialesInvalidasException;
import com.empresa.documentos.auth2fa.domain.exception.OtpExpiradoException;
import com.empresa.documentos.auth2fa.domain.exception.UsuarioBloqueadoException;
import com.empresa.documentos.auth2fa.domain.model.IntentoLogin;
import com.empresa.documentos.auth2fa.domain.model.OtpVerificacion;
import com.empresa.documentos.auth2fa.domain.model.TokenSesion;
import com.empresa.documentos.auth2fa.domain.model.Usuario;

import java.time.LocalDateTime;

@Service
public class AuthService implements AuthPort {

    private final UserRepositoryPort repo;
    private final OtpPort otpPort;
    private final TokenPort tokenPort;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepositoryPort repo, OtpPort otpPort, TokenPort tokenPort) {
        this.repo = repo;
        this.otpPort = otpPort;
        this.tokenPort = tokenPort;
    }

    @Override
    public String login(String username, String password) {
        Usuario u = repo.buscarPorUsername(username)
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales invalidas"));

        if (!u.estaActivo()) {
            throw new UsuarioBloqueadoException("Cuenta bloqueada temporalmente por multiples intentos fallidos");
        }

        // Validacion de hash BCrypt
        if (!passwordEncoder.matches(password, u.getPasswordHash())) {
            IntentoLogin fallo = new IntentoLogin(null, u.getId(), LocalDateTime.now(), false);
            u.registrarFalloLogin(fallo);
            repo.actualizarUsuario(u);
            repo.registrarIntentoLogin(fallo);

            if (!u.estaActivo()) {
                throw new UsuarioBloqueadoException("Usuario bloqueado tras alcanzar 3 intentos fallidos");
            }
            int restantes = 3 - u.getIntentosFallidos();
            throw new CredencialesInvalidasException("Credenciales invalidas. Intentos restantes: " + restantes);
        }

        // Login correcto: limpia contador de fallos y registra auditoria
        u.resetearIntentos();
        repo.actualizarUsuario(u);
        repo.registrarIntentoLogin(new IntentoLogin(null, u.getId(), LocalDateTime.now(), true));

        return otpPort.generarYEnviar(u);
    }

    @Override
    public String verificarOtp(String username, String codigo) {
        Usuario u = repo.buscarPorUsername(username)
                .orElseThrow(() -> new CredencialesInvalidasException("Usuario no encontrado"));

        if (!u.estaActivo()) {
            throw new UsuarioBloqueadoException("El usuario se encuentra bloqueado");
        }

        OtpVerificacion otp = repo.buscarUltimoOtpPorUsuario(u.getId())
                .orElseThrow(() -> new OtpExpiradoException("No existe un codigo OTP pendiente para este usuario"));

        if (otp.isUsado()) {
            throw new OtpExpiradoException("El codigo OTP ya fue utilizado o invalidado por superar los 3 intentos");
        }

        if (otp.estaExpirado()) {
            throw new OtpExpiradoException("El codigo OTP ha expirado (tiempo limite de 5 minutos superado)");
        }

        // Reto Ej. 4: contabilizar intentos errados de OTP y bloquear al 3er fallo
        if (!otp.getCodigo().equals(codigo)) {
            otp.registrarIntentoFallido();
            repo.actualizarOtp(otp);

            if (otp.isUsado()) {
                throw new OtpExpiradoException("Codigo OTP invalidado automaticamente tras 3 intentos fallidos");
            }
            int restantes = 3 - otp.getIntentosFallidos();
            throw new CredencialesInvalidasException("Codigo OTP incorrecto. Intentos restantes: " + restantes);
        }

        repo.marcarOtpUsado(otp.getId());
        TokenSesion sesion = tokenPort.generarToken(u.getId());
        repo.guardarToken(sesion);

        return sesion.getToken();
    }

    @Override
    public String reenviarOtp(String username) {
        Usuario u = repo.buscarPorUsername(username)
                .orElseThrow(() -> new CredencialesInvalidasException("Usuario no encontrado"));

        if (!u.estaActivo()) {
            throw new UsuarioBloqueadoException("El usuario se encuentra bloqueado");
        }

        return otpPort.generarYEnviar(u);
    }
}
