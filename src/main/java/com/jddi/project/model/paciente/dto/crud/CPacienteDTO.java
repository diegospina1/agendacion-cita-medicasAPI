package com.jddi.project.model.paciente.dto.crud;

import com.jddi.project.model.datos.persona.dto.CPersonaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CPacienteDTO(
        @Valid
        CPersonaDTO datosPaciente,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "^\\d{10}$")
        String telefono
) {
}
