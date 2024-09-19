package com.jddi.project.service.telefono.rel;

import com.jddi.project.model.persona.Persona;
import com.jddi.project.model.telefono.Telefono;
import com.jddi.project.model.telefono.dto.ActualizarTelefonoDTO;
import com.jddi.project.model.telefono.dto.CrearTelefonoDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;
import com.jddi.project.model.telefono.rel.TelefonoPersona;

import java.util.List;
import java.util.Set;

public interface ITelefonoPersonaService {

    TelefonoPersona crear(Persona persona, CrearTelefonoDTO datos);

    void establecerPrincipal(TelefonoPersona asociacion);

    RespuestaTelefonoDTO mappearRespuesta(Telefono telefono, Boolean activo);

    RespuestaTelefonoDTO buscarTelefonoPorPersonaId(Long personaId);

    Set<TelefonoPersona> buscarTelefonosAsociados(Persona persona);

    void eliminarRelacion(Persona persona, ActualizarTelefonoDTO telefono);

    List<RespuestaTelefonoDTO> buscarTelefonosAsociadosActivos(Persona persona);
}
