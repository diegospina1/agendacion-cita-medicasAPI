package com.jddi.project.service.datos.contacto;

import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.persona.dto.UPersonaDTO;
import com.jddi.project.service.datos.contacto.email.rel.IEmailPersonaService;
import com.jddi.project.service.datos.contacto.telefono.rel.ITelefonoPersonaService;

public interface IContactoService extends IEmailPersonaService, ITelefonoPersonaService {

    void actualizarDatosContacto(Persona persona, UPersonaDTO datos);

}
