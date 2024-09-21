package com.jddi.project.mapper;

import com.jddi.project.mapper.datos.PersonaMapper;
import com.jddi.project.model.datos.contacto.email.Email;
import com.jddi.project.model.datos.contacto.email.dto.CorreosDTO;
import com.jddi.project.model.paciente.Paciente;
import com.jddi.project.model.paciente.dto.DatosPacienteDTO;
import com.jddi.project.model.paciente.dto.crud.RPacienteDTO;
import com.jddi.project.model.datos.contacto.telefono.Telefono;
import com.jddi.project.model.datos.contacto.telefono.dto.TelefonosDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PacienteMapper extends PersonaMapper {

    PacienteMapper mapperPaciente = Mappers.getMapper(PacienteMapper.class);

    @Mapping(target = "id", source = "paciente.id")
    @Mapping(target = "paciente", source = "paciente.persona")
    @Mapping(target = "correoPrincipal", source = "email")
    @Mapping(target = "telefonoPrincipal", source = "telefono")
    DatosPacienteDTO toPacienteDTO(Paciente paciente, Email email, Telefono telefono);

    @Mapping(target = "id", source = "paciente.id")
    @Mapping(target = "paciente", source = "paciente.persona")
    RPacienteDTO toRPacienteDTO(Paciente paciente);

    @Mapping(target = "id", source = "paciente.id")
    @Mapping(target = "datos", source = "paciente.persona")
    @Mapping(target = "emails",
            expression = "java(paciente.getPersona().getEmails().stream().map(ep -> toEmailDTO(ep.getEmail())).toList())")
    CorreosDTO toEmailsPacienteDTO(Paciente paciente);

    @Mapping(target = "id", source = "paciente.id")
    @Mapping(target = "datos", source = "paciente.persona")
    @Mapping(target = "telefonos",
            expression = "java(paciente.getPersona().getTelefonos().stream().map(tp -> toTelefonoDTO(tp.getTelefono())).toList())")
    TelefonosDTO toTelefonosPacienteDTO(Paciente paciente);

}
