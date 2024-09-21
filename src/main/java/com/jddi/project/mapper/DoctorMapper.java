package com.jddi.project.mapper;

import com.jddi.project.mapper.datos.PersonaMapper;
import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.model.doctor.dto.DatosDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.RDoctorDTO;
import com.jddi.project.model.datos.contacto.email.Email;
import com.jddi.project.model.datos.contacto.email.dto.CorreosDTO;
import com.jddi.project.model.datos.contacto.telefono.Telefono;
import com.jddi.project.model.datos.contacto.telefono.dto.TelefonosDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DoctorMapper extends PersonaMapper {

    DoctorMapper mapperDoctor = Mappers.getMapper(DoctorMapper.class);

    @Mapping(target = "id", source = "doctor.id")
    @Mapping(target = "doctor", source = "doctor.persona")
    @Mapping(target = "correoPrincipal", source = "email")
    @Mapping(target = "telefonoPrincipal", source = "telefono")
    DatosDoctorDTO toDoctorDTO(Doctor doctor, Email email, Telefono telefono);

    @Mapping(target = "id", source = "doctor.id")
    @Mapping(target = "doctor", source = "doctor.persona")
    RDoctorDTO toRDoctorDTO(Doctor doctor);

    @Mapping(target = "id", source = "doctor.id")
    @Mapping(target = "datos", source = "doctor.persona")
    @Mapping(target = "emails",
            expression = "java(doctor.getPersona().getEmails().stream().map(ep -> toEmailDTO(ep.getEmail())).toList())")
    CorreosDTO toEmailsDoctorDTO(Doctor doctor);

    @Mapping(target = "id", source = "doctor.id")
    @Mapping(target = "datos", source = "doctor.persona")
    @Mapping(target = "telefonos",
            expression = "java(doctor.getPersona().getTelefonos().stream().map(tp -> toTelefonoDTO(tp.getTelefono())).toList())")
    TelefonosDTO toTelefonosDoctorDTO(Doctor doctor);
}
