package com.jddi.project.service.email.mapper;

import com.jddi.project.model.email.Email;
import com.jddi.project.model.email.rel.EmailPersona;
import com.jddi.project.model.persona.Persona;
import org.springframework.stereotype.Service;

@Service
public class EmailPersonaMapper {

    public EmailPersona mappearEmailPersona(Persona persona, Email email){
        return EmailPersona.builder()
                .email(email)
                .persona(persona)
                .principal(false)
                .activo(true)
                .build();
    }
}
