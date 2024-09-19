package com.jddi.project.service.telefono;

import com.jddi.project.dao.telefono.TelefonoRepository;
import com.jddi.project.model.telefono.Telefono;
import com.jddi.project.model.telefono.dto.ActualizarTelefonoDTO;
import com.jddi.project.model.telefono.dto.CrearTelefonoDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;
import com.jddi.project.service.telefono.mapper.TelefonoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService implements ITelefonoService{

    private final TelefonoRepository repository;
    private final TelefonoMapper mapper;

    public TelefonoService(TelefonoRepository repository, TelefonoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Telefono crear(CrearTelefonoDTO datos) {
        Optional<Telefono> buscarTelefono = buscarPorTelefono(datos.telefono());

        return buscarTelefono.orElseGet(() -> repository.save(mapper.mappearTelefono(datos)));
    }

    @Override
    public List<Telefono> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Telefono buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Optional<Telefono> buscarPorTelefono(String telefono) {
        return repository.findByTelefono(telefono);
    }

    @Override
    public void actualizarTelefono(Long id, ActualizarTelefonoDTO datos) {
        Telefono telefono = buscarPorId(id);
        telefono.actualizar(datos);
        repository.save(telefono);
    }

    @Override
    public RespuestaTelefonoDTO mappearRespuesta(Telefono telefono, Boolean activo){
        return mapper.mappearRespuesta(telefono, activo);
    }
}
