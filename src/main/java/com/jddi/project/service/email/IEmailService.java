package com.jddi.project.service.email;

import com.jddi.project.model.email.Email;
import com.jddi.project.model.email.dto.ActualizarEmailDTO;
import com.jddi.project.model.email.dto.CrearEmailDTO;
import com.jddi.project.model.email.dto.RespuestaEmailDTO;

public interface IEmailService {

    Email crear(CrearEmailDTO datos);

    Email buscarPorEmail(String email);

    Email buscarPorId(Long id);

    void actualizar(Long id, ActualizarEmailDTO datos);

    RespuestaEmailDTO mappearRespuesta(Email email, Boolean activo);
}
