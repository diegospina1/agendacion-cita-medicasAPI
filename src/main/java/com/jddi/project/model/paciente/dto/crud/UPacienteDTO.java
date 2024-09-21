package com.jddi.project.model.paciente.dto.crud;

import com.jddi.project.model.datos.persona.dto.UPersonaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UPacienteDTO(
        @NotNull
        Long id,
        @Valid
        UPersonaDTO datosActualizar
) {
}
