package com.jddi.project.model.datos.contacto.email.dto.crud;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
        @NotBlank
        @Email
        String email
){
}
