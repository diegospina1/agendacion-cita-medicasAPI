package com.jddi.project.model.datos.contacto.telefono.dto;

import com.jddi.project.model.datos.persona.dto.RPersonaDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.RTelefonoDTO;

import java.util.List;

public record TelefonosDTO(
        Long id,
        RPersonaDTO datos,
        List<RTelefonoDTO> telefonos
) {
}
