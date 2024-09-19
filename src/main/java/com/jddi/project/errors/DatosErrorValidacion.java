package com.jddi.project.errors;

public record DatosErrorValidacion(
        String campo,
        String error
) {
}
