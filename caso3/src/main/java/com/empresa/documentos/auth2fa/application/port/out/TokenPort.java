package com.empresa.documentos.auth2fa.application.port.out;

import com.empresa.documentos.auth2fa.domain.model.TokenSesion;

public interface TokenPort {
    TokenSesion generarToken(Long usuarioId);
}
