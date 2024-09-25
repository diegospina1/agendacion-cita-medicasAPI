package com.jddi.project.service.auth;

import com.jddi.project.config.security.DatosJWTToken;
import com.jddi.project.model.usuario.dto.DatosAutenticacionUsuario;
import jakarta.validation.Valid;

public interface IAuthService {

    DatosJWTToken login(DatosAutenticacionUsuario datos);

    DatosJWTToken register(DatosAutenticacionUsuario datos);
}
