package com.empresa.documentos.auth2fa.domain.exception;

public class OtpExpiradoException extends RuntimeException {
    public OtpExpiradoException(String message) {
        super(message);
    }
}
