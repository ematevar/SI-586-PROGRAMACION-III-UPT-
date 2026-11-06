package com.empresa.documentos.auth2fa.application.port.in;

import com.empresa.documentos.auth2fa.domain.model.Usuario;

public interface OtpPort {
    String generarYEnviar(Usuario usuario);
}
