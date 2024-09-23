package com.jddi.project.service.consulta.validaciones.doctor;

import com.jddi.project.dao.consulta.ConsultaRespository;
import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.service.consulta.validaciones.ValidadorCondiciones;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DoctorConConsulta implements ValidadorCondiciones {

    private final ConsultaRespository consultaRespository;

    public DoctorConConsulta(ConsultaRespository consultaRespository) {
        this.consultaRespository = consultaRespository;
    }

    @Override
    public void validar(Consulta consulta) {
        List<Consulta> consultasDoctor = consultaRespository.encontrarTodasNoCanceladasDoctorId(consulta.getDoctor().getId());
        for (Consulta consultaFor : consultasDoctor){
            if(consultaFor.getFecha_consulta().equals(consulta.getFecha_consulta()) && !consultaFor.equals(consulta)){
                System.out.println(consultaFor.getId());
                System.out.println(consulta.getId());
                throw new ValidationException("Doctor no disponible para la fecha indicada.");
            }
        }
    }
}
