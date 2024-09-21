package com.jddi.project.model.datos.contacto.telefono.dto.crud;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TelefonoDTO(
        @NotBlank
        @Pattern(regexp = "^\\d{10}$")
        String telefono
){
}
