package com.empresa.documentos.auth2fa.infrastructure.adapter.out.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.empresa.documentos.auth2fa.application.port.out.SmsPort;

// Reto Ej. 5: Adaptador secundario para envio de SMS simulado en consola
@Component
public class SmsAdapter implements SmsPort {

    private static final Logger log = LoggerFactory.getLogger(SmsAdapter.class);

    @Override
    public void enviarCodigo(String destino, String codigo) {
        log.info("[SMS-GATEWAY] Destino: {} | Mensaje: Su codigo de seguridad es {} (expira en 5 min)", destino, codigo);
    }
}
