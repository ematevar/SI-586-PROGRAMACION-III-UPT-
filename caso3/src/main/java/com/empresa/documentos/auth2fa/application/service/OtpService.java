package com.empresa.documentos.auth2fa.application.service;

import org.springframework.stereotype.Service;
import com.empresa.documentos.auth2fa.application.port.in.OtpPort;
import com.empresa.documentos.auth2fa.application.port.out.MailPort;
import com.empresa.documentos.auth2fa.application.port.out.SmsPort;
import com.empresa.documentos.auth2fa.application.port.out.UserRepositoryPort;
import com.empresa.documentos.auth2fa.domain.model.OtpVerificacion;
import com.empresa.documentos.auth2fa.domain.model.Usuario;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService implements OtpPort {

    private final UserRepositoryPort repo;
    private final MailPort mail;
    private final SmsPort sms;
    private final SecureRandom secureRandom = new SecureRandom();

    public OtpService(UserRepositoryPort repo, MailPort mail, SmsPort sms) {
        this.repo = repo;
        this.mail = mail;
        this.sms = sms;
    }

    @Override
    public String generarYEnviar(Usuario usuario) {
        repo.invalidarOtpsAnteriores(usuario.getId());

        String codigo = String.format("%06d", secureRandom.nextInt(1000000));
        OtpVerificacion otp = new OtpVerificacion(
                null,
                usuario.getId(),
                codigo,
                LocalDateTime.now().plusMinutes(5),
                false,
                0
        );
        repo.guardarOtp(otp);

        // Reto Ej. 5: Seleccion de canal (SMS o Correo) segun preferencia del usuario
        if (usuario.prefiereSms() && usuario.getTelefono() != null) {
            sms.enviarCodigo(usuario.getTelefono(), codigo);
            return "OTP enviado por SMS al numero registrado";
        } else {
            mail.enviarCodigo(usuario.getEmail(), codigo);
            return "OTP enviado al correo registrado";
        }
    }
}
