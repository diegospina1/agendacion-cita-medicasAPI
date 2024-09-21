package com.jddi.project.service.datos.persona;

import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.persona.dto.CPersonaDTO;
import com.jddi.project.model.datos.persona.dto.UPersonaDTO;

import java.util.List;

public interface IPersonaService {

    Persona crear(CPersonaDTO datos);

    List<Persona> listarTodos();

    Persona buscarId(Long id);

    Persona buscarDocumento(String documento);

    Persona actualizar(Long id, UPersonaDTO datos);

    void eliminar(Long id);

    void activar(Long id);

}
