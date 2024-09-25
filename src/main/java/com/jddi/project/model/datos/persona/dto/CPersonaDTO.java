package com.jddi.project.model.datos.persona.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CPersonaDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @NotBlank
        @Pattern(regexp = "^\\d{10}$")
        String documento
) {
}
