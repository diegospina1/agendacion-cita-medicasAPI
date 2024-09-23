package com.jddi.project.service.consulta;

import com.jddi.project.dao.consulta.ConsultaRespository;
import com.jddi.project.mapper.ConsultaMapper;
import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.model.consulta.dto.DatosConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.CConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.RConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.UConsultaDTO;
import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.paciente.Paciente;
import com.jddi.project.service.consulta.validaciones.ValidadorCondiciones;
import com.jddi.project.service.doctor.IDoctorService;
import com.jddi.project.service.paciente.IPacienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService implements IConsultaService {

    private final ConsultaRespository repository;
    private final IDoctorService doctorService;
    private final IPacienteService pacienteService;
    private final ConsultaMapper mapper;
    private final List<ValidadorCondiciones> validadorCondiciones;

    public ConsultaService(ConsultaRespository repository, IDoctorService doctorService, IPacienteService pacienteService,
                           ConsultaMapper mapper, List<ValidadorCondiciones> validadorCondiciones) {
        this.repository = repository;
        this.doctorService = doctorService;
        this.pacienteService = pacienteService;
        this.mapper = mapper;
        this.validadorCondiciones = validadorCondiciones;
    }

    @Override
    public DatosConsultaDTO crear(CConsultaDTO datos) {
        //Buscamos paciente, si no existe, la respectiva clase se encargará de lanzar la excepción
        Paciente paciente = pacienteService.buscarPacientePorId(datos.paciente_id());
        //Creamos la consulta
        Consulta consulta = new Consulta(paciente, datos.especialidad(), datos.fecha());
        //Buscamos un doctor para la consulta, que sea de la especialidad indicada.
        Doctor doctor = doctorService.buscarDoctorDisponible(consulta);
        consulta.setDoctor(doctor);
        validadorCondiciones.forEach(v -> v.validar(consulta));
        return mapper.toDatosConsultaDTO(repository.save(consulta));
    }

    @Override
    public List<RConsultaDTO> listarTodasPorPacienteId(Long id) {
        return repository.findAllByPacienteId(id).stream()
                .map(mapper::toRConsultaDTO)
                .sorted(Comparator.comparingLong(d -> calcularMasReciente(d.fecha_consulta())))
                .toList();
    }

    @Override
    public List<RConsultaDTO> listarTodasDoctorId(Long id) {
        return repository.findAllByDoctorId(id).stream()
                .map(mapper::toRConsultaDTO)
                .sorted(Comparator.comparingLong(d -> calcularMasReciente(d.fecha_consulta())))
                .toList();
    }

    @Override
    public List<RConsultaDTO> listarTodasPorEspecialidad(String especialidad) {
        try {
            Especialidad filtro = Especialidad.valueOf(especialidad.toUpperCase());
            return repository.findAllByEspecialidad(filtro).stream()
                    .map(mapper::toRConsultaDTO)
                    .sorted(Comparator.comparingLong(d -> calcularMasReciente(d.fecha_consulta())))
                    .toList();
        } catch (Exception e) {
            throw new ValidationException("Especialidad no encontrada.");
        }
    }

    @Override
    public DatosConsultaDTO listarPorId(Long id) {
        Optional<Consulta> buscarConsulta = repository.findById(id);
        if (buscarConsulta.isPresent()) {
            return mapper.toDatosConsultaDTO(buscarConsulta.get());
        } else {
            throw new EntityNotFoundException("Consulta no encontrada, id: " + id);
        }

    }

    @Override
    public RConsultaDTO actualizar(UConsultaDTO datos) {
        Optional<Consulta> buscarConsulta = repository.findById(datos.id());
        if (buscarConsulta.isPresent()) {
            Consulta consulta = buscarConsulta.get();
            actualizarDatosConsulta(consulta, datos);
            validadorCondiciones.forEach(v -> v.validar(consulta));
            return mapper.toRConsultaDTO(repository.save(consulta));
        } else {
            throw new EntityNotFoundException("Consulta no encontrada, id: " + datos.id());
        }
    }

    @Override
    public void cancelarConsulta(Long id) {
        Optional<Consulta> buscarConsulta = repository.findById(id);
        if (buscarConsulta.isPresent()) {
            Consulta consulta = buscarConsulta.get();
            consulta.cancelar();
            repository.save(consulta);
        } else {
            throw new EntityNotFoundException("Consulta no encontrada, id: " + id);
        }
    }

    //Apartir de una fecha, calcula su diferencia en segundos con el instante actual
    public Long calcularMasReciente(LocalDateTime fecha) {
        return Math.abs(fecha.toEpochSecond(ZoneOffset.UTC) - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    }

    public void actualizarDatosConsulta(Consulta consulta, UConsultaDTO datos) {
        if (datos.fecha() != null) {
            consulta.actualizar(datos.fecha());
        }
        if (datos.doctor_id() != null) {
            Doctor doctor = doctorService.buscarDoctorPorId(datos.doctor_id());
            consulta.setDoctor(doctor);
        }
    }

}
