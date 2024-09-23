package com.jddi.project.service.consulta;

import com.jddi.project.model.consulta.dto.DatosConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.CConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.RConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.UConsultaDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface IConsultaService {
    DatosConsultaDTO crear(@Valid CConsultaDTO datos);

    List<RConsultaDTO> listarTodasPorPacienteId(Long id);

    List<RConsultaDTO> listarTodasDoctorId(Long id);

    List<RConsultaDTO> listarTodasPorEspecialidad(String especialidad);

    DatosConsultaDTO listarPorId(Long id);

    RConsultaDTO actualizar(@Valid UConsultaDTO datos);

    void cancelarConsulta(Long id);
}
