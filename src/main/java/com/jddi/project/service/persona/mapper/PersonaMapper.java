package com.jddi.project.service.persona.mapper;

import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.paciente.Paciente;
import com.jddi.project.model.paciente.dto.DatosPacienteDTO;
import com.jddi.project.model.persona.Persona;
import com.jddi.project.model.persona.dto.CrearPersonaDTO;
import com.jddi.project.model.persona.dto.RespuestaPersonaDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;
import org.springframework.stereotype.Service;

@Service
public class PersonaMapper {

    public Persona mapearPersona(CrearPersonaDTO datos){
        return Persona.builder()
                .nombre(datos.nombre())
                .apellido(datos.apellido())
                .documento(datos.documento())
                .activo(true)
                .build();
    }

    public RespuestaPersonaDTO mapearRespuesta(Persona persona){
        return new RespuestaPersonaDTO(persona.getNombre(),
                persona.getApellido(), persona.getDocumento(), persona.getActivo());
    }

}
