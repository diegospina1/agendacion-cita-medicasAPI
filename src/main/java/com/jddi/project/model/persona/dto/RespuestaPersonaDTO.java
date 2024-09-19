package com.jddi.project.model.persona.dto;

public record RespuestaPersonaDTO(
        String nombre,
        String apellido,
        String documento,
        Boolean activo
) {
}
