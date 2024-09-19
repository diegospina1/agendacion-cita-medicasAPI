package com.jddi.project.model.paciente.dto;

import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.persona.dto.RespuestaPersonaDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;

public record DatosPacienteDTO(
        Long id,
        RespuestaPersonaDTO paciente,
        RespuestaEmailDTO correo,
        RespuestaTelefonoDTO telefono
) {
}
