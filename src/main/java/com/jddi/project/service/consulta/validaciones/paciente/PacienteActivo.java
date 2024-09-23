package com.jddi.project.service.consulta.validaciones.paciente;

import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.model.paciente.Paciente;
import com.jddi.project.service.consulta.validaciones.ValidadorCondiciones;
import com.jddi.project.service.paciente.PacienteService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorCondiciones {

    private final PacienteService pacienteService;

    public PacienteActivo(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    public void validar(Consulta consulta) {
        Paciente paciente = pacienteService.buscarPacientePorId(consulta.getPaciente().getId());
        if (!paciente.getPersona().getActivo()){
            throw new ValidationException("No se pudo agendar, motivo: Paciente inactivo.");
        }
    }
}
