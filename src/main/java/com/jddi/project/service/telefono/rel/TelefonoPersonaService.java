package com.jddi.project.service.telefono.rel;

import com.jddi.project.dao.telefono.TelefonoRepository;
import com.jddi.project.dao.telefono.rel.TelefonoPersonaRepository;
import com.jddi.project.model.persona.Persona;
import com.jddi.project.model.telefono.Telefono;
import com.jddi.project.model.telefono.dto.ActualizarTelefonoDTO;
import com.jddi.project.model.telefono.dto.CrearTelefonoDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;
import com.jddi.project.model.telefono.rel.TelefonoPersona;
import com.jddi.project.service.telefono.ITelefonoService;
import com.jddi.project.service.telefono.mapper.TelefonoPersonaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TelefonoPersonaService implements ITelefonoPersonaService {

    private final TelefonoPersonaRepository repository;
    private final ITelefonoService service;
    private final TelefonoPersonaMapper mapper;

    @Autowired
    public TelefonoPersonaService(TelefonoPersonaRepository repository, ITelefonoService service, TelefonoPersonaMapper mapper) {
        this.repository = repository;
        this.service = service;
        this.mapper = mapper;
    }


    @Override
    public TelefonoPersona crear(Persona persona, CrearTelefonoDTO datos) {
        Telefono telefono = service.crear(datos);
        List<TelefonoPersona> asociaciones = repository.findAllByPersona(persona);
        TelefonoPersona telefonoPersona = mapper.mappearAsociacion(persona, telefono);

        for (TelefonoPersona asociacion : asociaciones){
            if (asociacion.equals(telefonoPersona)){
                asociacion.activar();
                return repository.save(asociacion);
            } else {
                System.out.println("niunamenor");
            }
        }
        telefonoPersona.establecerPrincipal();
        return repository.save(telefonoPersona);
    }

    @Override
    public void establecerPrincipal(TelefonoPersona asociacion) {
        asociacion.establecerPrincipal();
        repository.save(asociacion);
        repository.desactivarOtros(asociacion);
    }

    @Override
    public RespuestaTelefonoDTO mappearRespuesta(Telefono telefono, Boolean activo) {
        return service.mappearRespuesta(telefono, activo);
    }

    @Override
    public RespuestaTelefonoDTO buscarTelefonoPorPersonaId(Long personaId) {
        TelefonoPersona telefonoPersona = repository.encontrarPrincipal(personaId);
        return mappearRespuesta(telefonoPersona.getTelefono(), telefonoPersona.getActivo());
    }

    @Override
    public Set<TelefonoPersona> buscarTelefonosAsociados(Persona persona) {
        return new HashSet<>(repository.findAllByPersona(persona));
    }

    @Override
    public void eliminarRelacion(Persona persona, ActualizarTelefonoDTO datos) {
        Telefono telefono = service.buscarPorTelefono(datos.telefono())
                .orElseThrow(EntityNotFoundException::new);

        TelefonoPersona telefonoPersona = repository.buscarAsociacion(persona, telefono)
                .orElseThrow(EntityNotFoundException::new);

        telefonoPersona.desactivar();
        repository.save(telefonoPersona);
    }

    @Override
    public List<RespuestaTelefonoDTO> buscarTelefonosAsociadosActivos(Persona persona) {
        List<TelefonoPersona> telefonos = repository.encontrarActivos(persona);
        return telefonos.stream()
                .map(t -> mappearRespuesta(t.getTelefono(), t.getActivo()))
                .sorted(Comparator.comparingLong(RespuestaTelefonoDTO::id))
                .collect(Collectors.toList());
    }
}
