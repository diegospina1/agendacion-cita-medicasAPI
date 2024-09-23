package com.jddi.project.dao.consulta;

import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.model.consulta.dto.DatosConsultaDTO;
import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.paciente.Paciente;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRespository extends JpaRepository<Consulta, Long> {

    @Query("select c from Consulta c where c.paciente.id = :id and c.cancelada = false")
    List<Consulta> encontrarTodasNoCanceladasPacienteId(Long id);
    List<Consulta> findAllByPacienteId(Long id);

    @Query("select c from Consulta c where c.doctor.id = :id and c.cancelada = false")
    List<Consulta> encontrarTodasNoCanceladasDoctorId(Long id);
    List<Consulta> findAllByDoctorId(Long id);

    @Query("select c from Consulta c where c.especialidad = :especialidad and c.cancelada = false")
    List<Consulta> findAllByEspecialidad(Especialidad especialidad);
}
