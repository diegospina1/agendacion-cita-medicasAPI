package com.jddi.project.model.paciente.dto;

import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.persona.dto.RespuestaPersonaDTO;

public record RespuestaPacienteDTO(
        Long id,
        RespuestaPersonaDTO paciente
) {
}
