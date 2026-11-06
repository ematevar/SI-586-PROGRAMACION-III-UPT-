package com.empresa.documentos.auth2fa.application.port.out;

// Reto Ej. 3: Segundo puerto de salida aplicando el mismo contrato conceptual
public interface SmsPort {
    void enviarCodigo(String destino, String codigo);
}
