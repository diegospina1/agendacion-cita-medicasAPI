package com.jddi.project.service.consulta;

import com.jddi.project.model.consulta.dto.DatosConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.CConsultaDTO;
import jakarta.validation.Valid;

public interface IConsultaService {
    DatosConsultaDTO crear(@Valid CConsultaDTO datos);
}
