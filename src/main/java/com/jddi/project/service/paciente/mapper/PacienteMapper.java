package com.jddi.project.service.paciente.mapper;

import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.paciente.Paciente;
import com.jddi.project.model.paciente.dto.DatosPacienteDTO;
import com.jddi.project.model.paciente.dto.RespuestaPacienteDTO;
import com.jddi.project.model.persona.dto.RespuestaPersonaDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;
import org.springframework.stereotype.Service;

@Service
public class PacienteMapper {

    public DatosPacienteDTO mapearDatosPaciente(Paciente paciente, RespuestaPersonaDTO persona,
                                                RespuestaEmailDTO email, RespuestaTelefonoDTO telefono){
        return new DatosPacienteDTO(paciente.getId(), persona, email, telefono);
    }
}
