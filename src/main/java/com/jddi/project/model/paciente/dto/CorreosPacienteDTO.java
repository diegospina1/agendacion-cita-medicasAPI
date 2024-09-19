package com.jddi.project.model.paciente.dto;

import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.persona.dto.RespuestaPersonaDTO;

import java.util.List;
import java.util.Set;

public record CorreosPacienteDTO(
        Long id,
        RespuestaPersonaDTO datos,
        List<RespuestaEmailDTO> emails
) {
}
