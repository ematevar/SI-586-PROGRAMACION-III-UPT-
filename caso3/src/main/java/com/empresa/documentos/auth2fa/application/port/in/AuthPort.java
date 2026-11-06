package com.empresa.documentos.auth2fa.application.port.in;

public interface AuthPort {
    String login(String username, String password);
    String verificarOtp(String username, String codigo);
    String reenviarOtp(String username);
}
