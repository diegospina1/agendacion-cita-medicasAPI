package com.jddi.project.model.consulta.dto.crud;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UConsultaDTO(
        @NotNull
        Long id,
        Long doctor_id,
        @Future
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fecha
) {
}
