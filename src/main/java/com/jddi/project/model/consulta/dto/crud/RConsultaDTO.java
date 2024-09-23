package com.jddi.project.model.consulta.dto.crud;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jddi.project.model.doctor.Especialidad;

import java.time.LocalDateTime;

public record RConsultaDTO(
        Long id,
        Long paciente_id,
        Long doctor_id,
        Especialidad especialidad,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fecha_consulta,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime duracion,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fecha_creacion,
        Boolean cancelada,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime ultima_modificacion
) {
}
