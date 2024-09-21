package com.jddi.project.mapper.datos;

import com.jddi.project.model.datos.contacto.email.Email;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.datos.contacto.email.dto.crud.REmailDTO;
import com.jddi.project.model.datos.contacto.email.rel.EmailPersona;
import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.contacto.telefono.Telefono;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.RTelefonoDTO;
import com.jddi.project.model.datos.contacto.telefono.rel.TelefonoPersona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactoMapper {

    //Email Mapper
    REmailDTO toEmailDTO(Email email);

    Email toEmail(EmailDTO datos);

    //Telefono Mapper
    RTelefonoDTO toTelefonoDTO(Telefono telefono);

    Telefono toTelefono(TelefonoDTO datos);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "persona", source = "persona")
    @Mapping(target = "telefono", source = "telefono")
    @Mapping(target = "principal", expression = "java(false)")
    @Mapping(target = "activo", expression = "java(true)")
    TelefonoPersona toTelefonoPersona(Persona persona, Telefono telefono);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "persona", source = "persona")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "principal", expression = "java(false)")
    @Mapping(target = "activo", expression = "java(true)")
    EmailPersona toEmailPersona(Persona persona, Email email);

}
