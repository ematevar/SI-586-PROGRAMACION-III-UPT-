package com.empresa.documentos.auth2fa.application.port.out;

public interface MailPort {
    void enviarCodigo(String destino, String codigo);
}
