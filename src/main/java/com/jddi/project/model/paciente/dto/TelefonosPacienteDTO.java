package com.jddi.project.model.paciente.dto;

import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.persona.dto.RespuestaPersonaDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;

import java.util.List;
import java.util.Set;

public record TelefonosPacienteDTO(
        Long id,
        RespuestaPersonaDTO datos,
        List<RespuestaTelefonoDTO> telefonos
) {
}
