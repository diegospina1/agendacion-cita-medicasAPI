package com.jddi.project.service.datos.contacto.telefono.rel;

import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import com.jddi.project.model.datos.contacto.telefono.rel.TelefonoPersona;

import java.util.List;

public interface ITelefonoPersonaService {

    TelefonoPersona asociarTelefono(Persona persona, TelefonoDTO datos);

    void establecerPrincipalTel(TelefonoPersona asociacion);

    TelefonoPersona buscarTelefonoPorPersona(Persona persona);

    List<TelefonoPersona> buscarTelefonosAsociadosActivos(Persona persona);

    List<TelefonoPersona> buscarTelefonosAsociados(Persona persona);

    void eliminarRelacionTel(Persona persona, TelefonoDTO telefono);
}
