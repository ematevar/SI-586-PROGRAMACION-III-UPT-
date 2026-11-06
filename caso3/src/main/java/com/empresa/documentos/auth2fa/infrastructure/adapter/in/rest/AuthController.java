package com.empresa.documentos.auth2fa.infrastructure.adapter.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.empresa.documentos.auth2fa.application.port.in.AuthPort;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthPort auth;

    public AuthController(AuthPort auth) {
        this.auth = auth;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String mensaje = auth.login(req.username(), req.password());
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verificar(@RequestBody OtpRequest req) {
        String token = auth.verificarOtp(req.username(), req.codigo());
        return ResponseEntity.ok(Map.of(
                "mensaje", "Autenticacion completada",
                "token", token
        ));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> reenviar(@RequestBody ResendRequest req) {
        String mensaje = auth.reenviarOtp(req.username());
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }
}

record LoginRequest(String username, String password) {}
record OtpRequest(String username, String codigo) {}
record ResendRequest(String username) {}
