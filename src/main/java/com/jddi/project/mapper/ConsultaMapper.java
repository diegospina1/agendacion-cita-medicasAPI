package com.jddi.project.mapper;

import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.model.consulta.dto.DatosConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.CConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.RConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.UConsultaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConsultaMapper extends DoctorMapper, PacienteMapper{

    ConsultaMapper mapperConsulta = Mappers.getMapper(ConsultaMapper.class);

    @Mapping(target = "informacion_paciente", source = "consulta.paciente")
    @Mapping(target = "informacion_doctor", source = "consulta.doctor")
    DatosConsultaDTO toDatosConsultaDTO(Consulta consulta);

    @Mapping(target = "paciente_id", source = "consulta.paciente.id")
    @Mapping(target = "doctor_id", source = "consulta.doctor.id")
    RConsultaDTO toRConsultaDTO(Consulta consulta);

}
