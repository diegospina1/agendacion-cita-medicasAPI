package com.jddi.project.service.datos.contacto.telefono.rel;

import com.jddi.project.dao.telefono.rel.TelefonoPersonaRepository;
import com.jddi.project.mapper.datos.ContactoMapper;
import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.contacto.telefono.Telefono;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import com.jddi.project.model.datos.contacto.telefono.rel.TelefonoPersona;
import com.jddi.project.service.datos.contacto.telefono.ITelefonoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TelefonoPersonaService implements ITelefonoPersonaService {

    private final TelefonoPersonaRepository repository;
    private final ITelefonoService service;
    private final ContactoMapper mapper;

    @Autowired
    public TelefonoPersonaService(TelefonoPersonaRepository repository, ITelefonoService service,
                                  @Qualifier("contactoMapperImpl") ContactoMapper mapper) {
        this.repository = repository;
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public TelefonoPersona asociarTelefono(Persona persona, TelefonoDTO datos) {
        //Service de telefono para crear
        Telefono telefono = service.crear(datos);
        //Creamos la asociacion con los argumentos del metodo
        TelefonoPersona telefonoPersona = mapper.toTelefonoPersona(persona, telefono);
        //Buscamos todos los telefonos asociados en la BD
        List<TelefonoPersona> asociaciones = repository.findAllByPersona(persona);

        //Iteramos la lista de telefonos asociados para ver si la relacion ya existe
        for (TelefonoPersona asociacion : asociaciones) {
            //Si existe, lo activa, lo guarda en BD y lo retorna.
            if (asociacion.equals(telefonoPersona)) {
                asociacion.activar();
                return repository.save(asociacion);
            }
        }
        //Si no existe, lo crea y lo marca como principal
        telefonoPersona.establecerPrincipal();
        return repository.save(telefonoPersona);
    }

    @Override
    public void establecerPrincipalTel(TelefonoPersona asociacion) {
        asociacion.establecerPrincipal();
        repository.save(asociacion);
        repository.desactivarOtros(asociacion);
    }

    @Override
    public TelefonoPersona buscarTelefonoPorPersona(Persona persona) {
        return repository.encontrarPrincipal(persona);
    }

    @Override
    public List<TelefonoPersona> buscarTelefonosAsociadosActivos(Persona persona) {
        //Recuperamos la lista de telefonos ACTIVOS en BD.
        List<TelefonoPersona> telefonosActivos = repository.encontrarActivos(persona);
        //Retornamos una lista ordenada de menor a mayor, teniendo en cuenta el "id" del telefono.
        return telefonosActivos.stream()
                .sorted(Comparator.comparingLong(tp -> tp.getTelefono().getId()))
                .toList();
    }

    @Override
    public List<TelefonoPersona> buscarTelefonosAsociados(Persona persona) {
        //Recupera la lista de todos los telefonos asociados, activos o no.
        List<TelefonoPersona> asociaciones = repository.findAllByPersona(persona);
        return asociaciones.stream()
                .sorted(Comparator.comparingLong(tp -> tp.getTelefono().getId()))
                .toList();
    }


    @Override
    public void eliminarRelacionTel(Persona persona, TelefonoDTO datos) {
        //Verificamos que exista el telefono.
        Telefono telefono = service.buscarPorTelefono(datos.telefono());
        //Verificamos que exista la asociacion
        Optional<TelefonoPersona> telefonoPersona = repository.buscarAsociacion(persona, telefono);

        if (telefonoPersona.isPresent()){
            //Desactivamos (eliminamos) la asociacion de la persona con el telefono.
            TelefonoPersona asociacion = telefonoPersona.get();
            repository.save(asociacion);
        } else {
            //Si no existe lanza exepcion
            throw new EntityNotFoundException("La relación no existe");
        }



    }

}
