package com.jddi.project.service.datos.contacto.email;

import com.jddi.project.model.datos.contacto.email.Email;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;

public interface IEmailService {

    Email crear(EmailDTO datos);

    Email buscarPorEmail(String email);

    Email buscarPorId(Long id);

    void actualizarEmail(Long id, EmailDTO datos);

}
