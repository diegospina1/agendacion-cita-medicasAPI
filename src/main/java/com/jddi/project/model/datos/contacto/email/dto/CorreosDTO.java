package com.jddi.project.model.datos.contacto.email.dto;

import com.jddi.project.model.datos.contacto.email.dto.crud.REmailDTO;
import com.jddi.project.model.datos.persona.dto.RPersonaDTO;

import java.util.List;

public record CorreosDTO(
        Long id,
        RPersonaDTO datos,
        List<REmailDTO> emails
) {
}
