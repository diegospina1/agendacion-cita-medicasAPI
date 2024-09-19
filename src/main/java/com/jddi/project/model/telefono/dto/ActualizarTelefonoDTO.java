package com.jddi.project.model.telefono.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ActualizarTelefonoDTO(
        @NotBlank
        @Pattern(regexp = "^\\d{10}$")
        String telefono
) {
}
