package com.jddi.project.service.consulta.validaciones.doctor;

import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.service.consulta.validaciones.ValidadorCondiciones;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class DoctorEspecialidad implements ValidadorCondiciones {
    @Override
    public void validar(Consulta consulta) {
        if (!consulta.getEspecialidad().equals(consulta.getDoctor().getEspecialidad())){
            throw new ValidationException("Doctor inválido para la solicitud.");
        }
    }
}
