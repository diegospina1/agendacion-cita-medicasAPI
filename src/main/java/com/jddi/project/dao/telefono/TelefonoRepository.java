package com.jddi.project.dao.telefono;

import com.jddi.project.model.datos.contacto.telefono.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, Long> {

    Optional<Telefono> findByTelefono(String telefono);
}
