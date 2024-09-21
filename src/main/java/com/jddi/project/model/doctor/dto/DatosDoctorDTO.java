package com.jddi.project.model.doctor.dto;

import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.datos.contacto.email.dto.crud.REmailDTO;
import com.jddi.project.model.datos.persona.dto.RPersonaDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.RTelefonoDTO;

public record DatosDoctorDTO(
        Long id,
        RPersonaDTO doctor,
        Especialidad especialidad,
        REmailDTO correoPrincipal,
        RTelefonoDTO telefonoPrincipal

) {
}
