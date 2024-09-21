package com.jddi.project.service.datos.persona;

import com.jddi.project.dao.persona.PersonaRepository;
import com.jddi.project.mapper.datos.PersonaMapper;
import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.persona.dto.CPersonaDTO;
import com.jddi.project.model.datos.persona.dto.UPersonaDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService implements IPersonaService {

    private final PersonaRepository repository;
    private final PersonaMapper mapper;

    @Autowired
    public PersonaService(PersonaRepository repository, @Qualifier("personaMapperImpl") PersonaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Persona crear(CPersonaDTO datos) {
        Persona persona = mapper.toPersona(datos);
        return repository.save(persona);
    }

    @Override
    public List<Persona> listarTodos() {
        return repository.findAllByActivoTrue();
    }

    @Override
    public Persona buscarId(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Persona buscarDocumento(String documento) {
        return repository.findByDocumento(documento);
    }

    @Override
    public Persona actualizar(Long id, UPersonaDTO datos) {
        Persona persona = buscarId(id);
        persona.actualizar(datos);
        return repository.save(persona);
    }

    @Override
    public void eliminar(Long id) {
        Persona persona = buscarId(id);
        persona.desactivar();
        repository.save(persona);
    }

    @Override
    public void activar(Long id) {
        Persona persona = buscarId(id);
        persona.activar();
        repository.save(persona);
    }
}
