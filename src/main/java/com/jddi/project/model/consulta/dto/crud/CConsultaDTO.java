package com.jddi.project.model.consulta.dto.crud;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jddi.project.model.doctor.Especialidad;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CConsultaDTO(
        @NotNull
        Long paciente_id,
        @NotNull
        @Future
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fecha,
        @NotNull
        Especialidad especialidad
) {
}
