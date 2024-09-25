package com.jddi.project.controller;

import com.jddi.project.config.security.DatosJWTToken;
import com.jddi.project.model.usuario.dto.DatosAutenticacionUsuario;
import com.jddi.project.service.auth.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final IAuthService authService;

    public AuthenticationController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<DatosJWTToken> login(@RequestBody @Valid DatosAutenticacionUsuario datos){
        return ResponseEntity.ok(authService.login(datos));
    }

    @PostMapping("/register")
    public ResponseEntity<DatosJWTToken> register(@RequestBody @Valid DatosAutenticacionUsuario datos){
        return ResponseEntity.ok(authService.register(datos));
    }
}
