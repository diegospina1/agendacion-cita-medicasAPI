package com.jddi.project.dao.paciente;

import com.jddi.project.model.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("select p from Paciente p where p.persona.activo = true")
    List<Paciente> encontrarActivos();

    @Query("select p from Paciente p where p.persona.documento = :#{#documento}")
    Optional<Paciente> findByDocumento(@Param("documento") String documento);
}
