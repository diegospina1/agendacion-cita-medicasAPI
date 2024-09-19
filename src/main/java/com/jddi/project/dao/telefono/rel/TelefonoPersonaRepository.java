package com.jddi.project.dao.telefono.rel;

import com.jddi.project.model.persona.Persona;
import com.jddi.project.model.telefono.Telefono;
import com.jddi.project.model.telefono.rel.TelefonoPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelefonoPersonaRepository extends JpaRepository<TelefonoPersona, Long> {

    List<TelefonoPersona> findAllByPersona(Persona persona);

    @Query("update TelefonoPersona p set p.principal = false where p.persona = :#{#asociacion.persona} and p.id != :#{#asociacion.id}")
    @Modifying
    void desactivarOtros(@Param("asociacion") TelefonoPersona asociacion);

    @Query("select tp from TelefonoPersona tp where tp.persona.id = :#{#personaId} and tp.principal = true")
    TelefonoPersona encontrarPrincipal(@Param("personaId") Long personaId);

    @Query("select tp from TelefonoPersona tp where tp.persona = :#{#p} and tp.telefono = :#{#t}")
    Optional<TelefonoPersona> buscarAsociacion(@Param("p") Persona persona, @Param("t") Telefono telefono);

    @Query("select tp from TelefonoPersona tp where tp.persona = :#{#p} and tp.activo = true")
    List<TelefonoPersona> encontrarActivos(@Param("p") Persona persona);
}
