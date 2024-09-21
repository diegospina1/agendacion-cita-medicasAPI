package com.jddi.project.model.paciente.dto;

import com.jddi.project.model.datos.contacto.email.dto.crud.REmailDTO;
import com.jddi.project.model.datos.persona.dto.RPersonaDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.RTelefonoDTO;

public record DatosPacienteDTO(
        Long id,
        RPersonaDTO paciente,
        REmailDTO correoPrincipal,
        RTelefonoDTO telefonoPrincipal
) {
}
