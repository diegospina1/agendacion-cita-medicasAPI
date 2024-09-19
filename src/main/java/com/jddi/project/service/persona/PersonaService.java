package com.jddi.project.service.persona;

import com.jddi.project.dao.persona.PersonaRepository;
import com.jddi.project.model.paciente.dto.DatosPacienteDTO;
import com.jddi.project.model.persona.Persona;
import com.jddi.project.model.persona.dto.ActualizarPersonaDTO;
import com.jddi.project.model.persona.dto.CrearPersonaDTO;
import com.jddi.project.model.persona.dto.RespuestaPersonaDTO;
import com.jddi.project.service.persona.mapper.PersonaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService implements IPersonaService {

    private final PersonaRepository repository;
    private final PersonaMapper mapper;

    @Autowired
    public PersonaService(PersonaRepository repository, PersonaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Persona crear(CrearPersonaDTO datos) {
        Persona persona = mapper.mapearPersona(datos);
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
    public Persona actualizar(Long id, ActualizarPersonaDTO datos) {
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

    @Override
    public RespuestaPersonaDTO mapearRespuesta(Persona persona) {
        return mapper.mapearRespuesta(persona);
    }
}
