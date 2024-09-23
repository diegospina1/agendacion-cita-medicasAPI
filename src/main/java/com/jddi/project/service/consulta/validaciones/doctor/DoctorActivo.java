package com.jddi.project.service.consulta.validaciones.doctor;

import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.service.consulta.validaciones.ValidadorCondiciones;
import com.jddi.project.service.doctor.DoctorService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class DoctorActivo implements ValidadorCondiciones {

    private final DoctorService doctorService;

    public DoctorActivo(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public void validar(Consulta consulta) {
        Doctor doctor = doctorService.buscarDoctorPorId(consulta.getDoctor().getId());
        if (!doctor.getPersona().getActivo()){
            throw new ValidationException("No se pudo agendar, motivo: Doctor inactivo.");
        }
    }
}
