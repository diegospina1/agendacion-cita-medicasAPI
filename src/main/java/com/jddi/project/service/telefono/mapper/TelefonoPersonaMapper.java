package com.jddi.project.service.telefono.mapper;

import com.jddi.project.model.persona.Persona;
import com.jddi.project.model.telefono.Telefono;
import com.jddi.project.model.telefono.rel.TelefonoPersona;
import org.springframework.stereotype.Service;

@Service
public class TelefonoPersonaMapper {

    public TelefonoPersona mappearAsociacion(Persona persona, Telefono telefono){
        return TelefonoPersona.builder()
                .persona(persona)
                .telefono(telefono)
                .principal(false)
                .activo(true)
                .build();
    }
}
