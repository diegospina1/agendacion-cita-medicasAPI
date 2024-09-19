package com.jddi.project.service.email.rel;

import com.jddi.project.model.email.Email;
import com.jddi.project.model.email.dto.ActualizarEmailDTO;
import com.jddi.project.model.email.dto.CrearEmailDTO;
import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.email.rel.EmailPersona;
import com.jddi.project.model.persona.Persona;

import java.util.List;
import java.util.Set;

public interface IEmailPersonaService {

    EmailPersona crear(Persona persona, CrearEmailDTO email);

    void establecerPrincipal(EmailPersona emailPersona);

    RespuestaEmailDTO mappearRespuesta(Email email, Boolean activo);

    RespuestaEmailDTO buscarEmailPorPersona(Persona persona);

    List<RespuestaEmailDTO> buscarCorreosAsociadosActivos(Persona persona);

    Set<EmailPersona> buscarCorreosAsociados(Persona persona);

    void eliminarRelacion(Persona id, ActualizarEmailDTO email);
}
