package com.empresa.documentos.auth2fa.domain.exception;

public class UsuarioBloqueadoException extends RuntimeException {
    public UsuarioBloqueadoException(String message) {
        super(message);
    }
}
