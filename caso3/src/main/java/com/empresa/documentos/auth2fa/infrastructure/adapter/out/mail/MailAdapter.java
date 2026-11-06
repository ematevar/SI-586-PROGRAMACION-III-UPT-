package com.empresa.documentos.auth2fa.infrastructure.adapter.out.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.empresa.documentos.auth2fa.application.port.out.MailPort;

@Component
public class MailAdapter implements MailPort {

    private static final Logger log = LoggerFactory.getLogger(MailAdapter.class);
    private final JavaMailSender sender;

    public MailAdapter(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void enviarCodigo(String destino, String codigo) {
        // Imprimimos siempre en consola para facilitar las pruebas locales de laboratorio
        log.info("[MAIL-OTP] Destinatario: {} | Codigo OTP: {} (valido 5 minutos)", destino, codigo);
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(destino);
            msg.setSubject("Codigo de verificacion - Acceso Institucional");
            msg.setText("Tu codigo OTP es: " + codigo + " (valido por 5 minutos).");
            sender.send(msg);
        } catch (Exception ex) {
            log.warn("SMTP no configurado o rechazado ({}). Usa el codigo OTP mostrado arriba en consola.", ex.getMessage());
        }
    }
}
