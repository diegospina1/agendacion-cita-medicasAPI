package com.jddi.project.service.auth;

import com.jddi.project.config.security.DatosJWTToken;
import com.jddi.project.config.security.TokenService;
import com.jddi.project.dao.usuario.UsuarioRepository;
import com.jddi.project.model.usuario.Usuario;
import com.jddi.project.model.usuario.dto.DatosAutenticacionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService{

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DatosJWTToken login(DatosAutenticacionUsuario datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.login(),
                datos.clave());
        Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);
        String JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return new DatosJWTToken(JWTtoken);
    }

    @Override
    public DatosJWTToken register(DatosAutenticacionUsuario datos) {
        Usuario usuario = new Usuario(null, datos.login(), passwordEncoder.encode(datos.clave()));
        Usuario respuesta = repository.save(usuario);
        return login(datos);
    }
}
