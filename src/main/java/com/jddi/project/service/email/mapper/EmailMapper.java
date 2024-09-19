package com.jddi.project.service.email.mapper;

import com.jddi.project.model.email.Email;
import com.jddi.project.model.email.dto.CrearEmailDTO;
import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import org.springframework.stereotype.Service;

@Service
public class EmailMapper {

    public Email mappearEmail(CrearEmailDTO datos){
        return Email.builder()
                .email(datos.email())
                .build();
    }

    public RespuestaEmailDTO mappearRespesta(Email email, Boolean activo){
        return new RespuestaEmailDTO(email.getId(), email.getEmail(), activo);
    }
}
