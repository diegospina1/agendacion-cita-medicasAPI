package com.jddi.project.service.datos.contacto.email.rel;

import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.datos.contacto.email.rel.EmailPersona;
import com.jddi.project.model.datos.persona.Persona;

import java.util.List;

public interface IEmailPersonaService {

    EmailPersona asociarEmail(Persona persona, EmailDTO email);

    void establecerPrincipalEmail(EmailPersona emailPersona);

    EmailPersona buscarEmailPorPersona(Persona persona);

    List<EmailPersona> buscarEmailsAsociadosActivos(Persona persona);

    List<EmailPersona> buscarEmailsAsociados(Persona persona);

    void eliminarRelacionEmail(Persona persona, EmailDTO email);
}
