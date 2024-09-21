package com.jddi.project.model.doctor.dto.crud;

import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.datos.persona.dto.RPersonaDTO;

public record RDoctorDTO(
        Long id,
        RPersonaDTO doctor,
        Especialidad especialidad
) {
}
