package com.jddi.project.model.paciente.dto;

import com.jddi.project.model.email.dto.CrearEmailDTO;
import com.jddi.project.model.persona.dto.CrearPersonaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CrearPacienteDTO(
        @Valid
        CrearPersonaDTO datosPaciente,
        @NotBlank
        @Email
        String email,
        String telefono
) {
}
