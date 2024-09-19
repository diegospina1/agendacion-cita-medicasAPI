package com.jddi.project.dao.persona;

import com.jddi.project.model.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    List<Persona> findAllByActivoTrue();

    Persona findByDocumento(String documento);
}
