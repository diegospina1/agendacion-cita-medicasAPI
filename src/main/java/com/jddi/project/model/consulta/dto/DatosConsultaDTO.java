package com.jddi.project.model.consulta.dto;

import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.doctor.dto.crud.RDoctorDTO;
import com.jddi.project.model.paciente.dto.crud.RPacienteDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record DatosConsultaDTO(
        Long id,
        RPacienteDTO paciente,
        RDoctorDTO doctor,
        Especialidad especialidad,
        LocalDateTime fecha_consulta,
        LocalDateTime duracion,
        LocalDateTime fecha_creacion
) {
}
