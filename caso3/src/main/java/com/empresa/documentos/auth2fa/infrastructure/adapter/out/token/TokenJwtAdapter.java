package com.empresa.documentos.auth2fa.infrastructure.adapter.out.token;

import org.springframework.stereotype.Component;
import com.empresa.documentos.auth2fa.application.port.out.TokenPort;
import com.empresa.documentos.auth2fa.domain.model.TokenSesion;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TokenJwtAdapter implements TokenPort {

    @Override
    public TokenSesion generarToken(Long usuarioId) {
        String rawToken = UUID.randomUUID().toString();
        return new TokenSesion(null, usuarioId, rawToken, LocalDateTime.now().plusHours(2));
    }
}
