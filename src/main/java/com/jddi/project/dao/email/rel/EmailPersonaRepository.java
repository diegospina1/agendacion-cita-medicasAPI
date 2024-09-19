package com.jddi.project.dao.email.rel;

import com.jddi.project.model.email.Email;
import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.email.rel.EmailPersona;
import com.jddi.project.model.persona.Persona;
import com.jddi.project.model.telefono.rel.TelefonoPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmailPersonaRepository extends JpaRepository<EmailPersona, Long> {

    List<EmailPersona> findAllByPersona(Persona persona);

    @Query("update EmailPersona e set e.principal = false where e.persona = :#{#relacion.persona} and e.id != :#{#relacion.id}")
    @Modifying
    void setPrincipalEmail(@Param("relacion") EmailPersona emailPersona);

    @Query("select ep from EmailPersona ep where ep.persona = :#{#p} and ep.principal = true")
    EmailPersona encontrarPrincipal(@Param("p") Persona persona);

    @Query("select ep from EmailPersona ep where ep.persona = :#{#p} and ep.email = :#{#e}")
    Optional<EmailPersona> buscarAsociacion(@Param("p") Persona persona, @Param("e") Email email);

    @Query("select ep from EmailPersona ep where ep.persona = :#{#p} and ep.activo = true")
    List<EmailPersona> encontrarActivos(@Param("p") Persona persona);
}
