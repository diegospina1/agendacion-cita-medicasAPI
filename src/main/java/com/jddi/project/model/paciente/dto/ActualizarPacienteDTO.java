package com.jddi.project.model.paciente.dto;

import com.jddi.project.model.email.dto.ActualizarEmailDTO;
import com.jddi.project.model.persona.dto.ActualizarPersonaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarPacienteDTO(
        @NotNull
        Long id,
        @Valid
        ActualizarPersonaDTO datosActualizar
) {
}
