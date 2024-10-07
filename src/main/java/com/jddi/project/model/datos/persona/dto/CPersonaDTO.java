package com.jddi.project.model.datos.persona.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CPersonaDTO(
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]+$")
        String nombre,
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]+$")
        String apellido,
        @NotBlank
        @Pattern(regexp = "^\\d{10}$")
        String documento
) {
}
