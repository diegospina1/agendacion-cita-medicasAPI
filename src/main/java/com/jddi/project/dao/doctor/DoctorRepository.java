package com.jddi.project.dao.doctor;

import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.doctor.dto.crud.RDoctorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findAllByEspecialidad(Especialidad filtro);

    @Query("select d from Doctor d where d.persona.documento = :#{#documento}")
    Optional<Doctor> findByDocumento(@Param("documento") String documento);

    @Query("select d from Doctor d where d.persona.activo = true and d.especialidad = :#{#especialidad}" +
            " and d.id not in(select c.doctor.id from Consulta c where ((:#{#fecha} between c.fecha_consulta and c.duracion) or " +
            " (:#{#duracion} between c.fecha_consulta and c.duracion) or (c.fecha_consulta between :#{#fecha} and :#{#duracion})))" +
            " order by rand() limit 1")
    Doctor buscarDoctorDisponible(@Param("especialidad") Especialidad especialidad,
                                  @Param("fecha") LocalDateTime fecha,
                                  @Param("duracion") LocalDateTime duracion);
}
