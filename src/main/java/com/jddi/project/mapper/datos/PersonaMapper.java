package com.jddi.project.mapper.datos;

import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.persona.dto.CPersonaDTO;
import com.jddi.project.model.datos.persona.dto.RPersonaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonaMapper extends ContactoMapper {

    RPersonaDTO toPersonaDTO(Persona persona);

    @Mapping(target = "activo", expression = "java(true)")
    Persona toPersona(CPersonaDTO datos);
}
