package com.jddi.project.mapper;

import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.model.consulta.dto.DatosConsultaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConsultaMapper extends DoctorMapper, PacienteMapper{

    ConsultaMapper mapperConsulta = Mappers.getMapper(ConsultaMapper.class);

    @Mapping(target = "paciente", source = "consulta.paciente")
    @Mapping(target = "doctor", source = "consulta.doctor")
    DatosConsultaDTO toDatosConsultaDTO(Consulta consulta);
}
