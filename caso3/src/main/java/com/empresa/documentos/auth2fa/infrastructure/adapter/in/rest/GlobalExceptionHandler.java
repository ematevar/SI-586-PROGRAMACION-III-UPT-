package com.empresa.documentos.auth2fa.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.empresa.documentos.auth2fa.domain.exception.CredencialesInvalidasException;
import com.empresa.documentos.auth2fa.domain.exception.OtpExpiradoException;
import com.empresa.documentos.auth2fa.domain.exception.UsuarioBloqueadoException;

import java.time.LocalDateTime;
import java.util.Map;

// Reto Ej. 6: Manejo centralizado de estados HTTP (401, 410, 423)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<?> handleCredencialesInvalidas(CredencialesInvalidasException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "codigo", 401,
                "error", "No autorizado",
                "mensaje", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(OtpExpiradoException.class)
    public ResponseEntity<?> handleOtpExpirado(OtpExpiradoException ex) {
        return ResponseEntity.status(HttpStatus.GONE).body(Map.of(
                "codigo", 410,
                "error", "Recurso expirado o invalidado",
                "mensaje", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
        ));
    }

    @ExceptionHandler(UsuarioBloqueadoException.class)
    public ResponseEntity<?> handleUsuarioBloqueado(UsuarioBloqueadoException ex) {
        return ResponseEntity.status(HttpStatus.LOCKED).body(Map.of(
                "codigo", 423,
                "error", "Cuenta bloqueada",
                "mensaje", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}
