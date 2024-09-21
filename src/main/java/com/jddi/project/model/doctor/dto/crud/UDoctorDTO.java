package com.jddi.project.model.doctor.dto.crud;

import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.datos.persona.dto.UPersonaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UDoctorDTO(
        @NotNull
        Long id,
        @Valid
        UPersonaDTO datosActualizar,
        Especialidad especialidad
) {
}
