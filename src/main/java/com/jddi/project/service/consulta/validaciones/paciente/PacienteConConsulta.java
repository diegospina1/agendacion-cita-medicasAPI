package com.jddi.project.service.consulta.validaciones.paciente;

import com.jddi.project.dao.consulta.ConsultaRespository;
import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.service.consulta.validaciones.ValidadorCondiciones;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PacienteConConsulta implements ValidadorCondiciones {

    private final ConsultaRespository consultaRespository;

    public PacienteConConsulta(ConsultaRespository consultaRespository) {
        this.consultaRespository = consultaRespository;
    }

    @Override
    public void validar(Consulta consulta) {
        List<Consulta> consultasPaciente = consultaRespository.encontrarTodasNoCanceladasPacienteId(consulta.getPaciente().getId());
        for (Consulta consultaFor : consultasPaciente){
            if (consultaFor.getFecha_consulta().equals(consulta.getFecha_consulta()) && !consultaFor.equals(consulta)){
                throw new ValidationException("El paciente ya tiene una consulta a esa hora.");
            }
        }
    }
}
