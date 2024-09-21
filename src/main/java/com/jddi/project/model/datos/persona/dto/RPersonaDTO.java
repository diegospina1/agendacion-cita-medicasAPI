package com.jddi.project.model.datos.persona.dto;

public record RPersonaDTO(
        String nombre,
        String apellido,
        String documento,
        Boolean activo
) {
}
