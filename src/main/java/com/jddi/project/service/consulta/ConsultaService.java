package com.jddi.project.service.consulta;

import com.jddi.project.dao.consulta.ConsultaRespository;
import com.jddi.project.mapper.ConsultaMapper;
import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.model.consulta.dto.DatosConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.CConsultaDTO;
import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.model.paciente.Paciente;
import com.jddi.project.service.consulta.validaciones.ValidadorConsulta;
import com.jddi.project.service.doctor.IDoctorService;
import com.jddi.project.service.paciente.IPacienteService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ConsultaService implements IConsultaService{

    private final ConsultaRespository respository;
    private final IDoctorService doctorService;
    private final IPacienteService pacienteService;
    private final ConsultaMapper mapper;
    private final List<ValidadorConsulta> validadorCondiciones;

    public ConsultaService(ConsultaRespository respository, IDoctorService doctorService, IPacienteService pacienteService, ConsultaMapper mapper,
                           List<ValidadorConsulta> validadorCondiciones) {
        this.respository = respository;
        this.doctorService = doctorService;
        this.pacienteService = pacienteService;
        this.mapper = mapper;
        this.validadorCondiciones = validadorCondiciones;
    }

    @Override
    public DatosConsultaDTO crear(CConsultaDTO datos){
        //Buscamos paciente, si no existe, la respectiva clase se encargará de lanzar la excepción
        Paciente paciente = pacienteService.buscarPacientePorId(datos.paciente_id());
        //Si llega hasta acá, es que existe, ahora hagamos las respectivas validaciones.
        validadorCondiciones.forEach(v -> v.validarConsulta(datos));
        LocalDateTime duracion = datos.fecha().plus(15, TimeUnit.MINUTES.toChronoUnit());
        Doctor doctor = doctorService.buscarDoctorDisponible(datos.especialidad(), datos.fecha(), duracion);
        if (doctor == null){
            throw new ValidationException("no existen medicos disponibles para este horario y especialidad");
        }
        Consulta consulta = respository.save(new Consulta(null, paciente, doctor,
                datos.especialidad(), datos.fecha(), duracion, LocalDateTime.now()));
        return mapper.toDatosConsultaDTO(consulta);
    }
}
