package com.jddi.project.model.paciente.dto.crud;

import com.jddi.project.model.datos.persona.dto.RPersonaDTO;

public record RPacienteDTO(
        Long id,
        RPersonaDTO paciente
) {
}
