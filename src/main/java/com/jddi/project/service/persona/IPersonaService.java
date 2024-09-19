package com.jddi.project.service.persona;

import com.jddi.project.model.persona.Persona;
import com.jddi.project.model.persona.dto.ActualizarPersonaDTO;
import com.jddi.project.model.persona.dto.CrearPersonaDTO;
import com.jddi.project.model.persona.dto.RespuestaPersonaDTO;

import java.util.List;

public interface IPersonaService {

    Persona crear(CrearPersonaDTO datos);

    List<Persona> listarTodos();

    Persona buscarId(Long id);

    Persona buscarDocumento(String documento);

    Persona actualizar(Long id, ActualizarPersonaDTO datos);

    void eliminar(Long id);

    void activar(Long id);

    RespuestaPersonaDTO mapearRespuesta(Persona persona);
}
