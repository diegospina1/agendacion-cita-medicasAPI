package com.jddi.project.model.doctor.dto.crud;

import com.jddi.project.model.datos.persona.dto.CPersonaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CDoctorDTO(
        @Valid
        CPersonaDTO datosDoctor,
        @NotBlank
        String especialidad,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "^\\d{10}$")
        String telefono
) {
}
