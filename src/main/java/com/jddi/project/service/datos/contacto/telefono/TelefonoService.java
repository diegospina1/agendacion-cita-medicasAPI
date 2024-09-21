package com.jddi.project.service.datos.contacto.telefono;

import com.jddi.project.dao.telefono.TelefonoRepository;
import com.jddi.project.mapper.datos.ContactoMapper;
import com.jddi.project.model.datos.contacto.telefono.Telefono;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelefonoService implements ITelefonoService {

    private final TelefonoRepository repository;
    private final ContactoMapper mapper;

    @Autowired
    public TelefonoService(TelefonoRepository repository, @Qualifier("contactoMapperImpl") ContactoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Telefono crear(TelefonoDTO datos) {
        //Primero buscamos el telefono, por si existe en la BD.
        Optional<Telefono> buscarTelefono = repository.findByTelefono(datos.telefono());
        //retornamos el telefono encontrado, o si no, lo creamos
        return buscarTelefono
                .orElseGet(() -> repository.save(mapper.toTelefono(datos)));
    }

    @Override
    public Telefono buscarPorTelefono(String telefono) {
        //Buscamos el telefono en la BD, si no lo encuentra manda una exepcion
        return repository.findByTelefono(telefono)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Telefono buscarPorId(Long id) {
        //Buscamos el telefono por su id, si no lo encuentra manda una exepcion
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void actualizarTelefono(Long id, TelefonoDTO datos) {
        //Lo buscamos por su id.
        Telefono telefono = buscarPorId(id);
        //Usamos el metodo actualizar de la entidad telefono.
        telefono.actualizar(datos);
        //Guardamos cambios.
        repository.save(telefono);
    }
}
