package com.jddi.project.model.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ActualizarEmailDTO(
        @NotBlank
        @Email
        String email
) {
}
