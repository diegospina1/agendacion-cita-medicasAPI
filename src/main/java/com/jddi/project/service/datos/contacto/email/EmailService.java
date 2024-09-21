package com.jddi.project.service.datos.contacto.email;

import com.jddi.project.dao.email.EmailRepository;
import com.jddi.project.mapper.datos.ContactoMapper;
import com.jddi.project.model.datos.contacto.email.Email;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService implements IEmailService{

    private final EmailRepository repository;
    private final ContactoMapper mapper;

    @Autowired
    public EmailService(EmailRepository repository, @Qualifier("contactoMapperImpl") ContactoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Email crear(EmailDTO datos){
        //Primero buscamos el email, por si existe en la BD.
        Optional<Email> buscarEmail = repository.findByEmail(datos.email());
        //retornamos el Email encontrado, o si no, lo creamos
        return buscarEmail
                .orElseGet(() -> repository.save(mapper.toEmail(datos)));
    }

    @Override
    public Email buscarPorEmail(String email){
        //Buscamos el email en la BD, si no lo encuentra manda una exepcion
        return repository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Email buscarPorId(Long id){
        //Buscamos el email por su id, si no lo encuentra manda una exepcion
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void actualizarEmail(Long id, EmailDTO datos){
        //Lo buscamos por su id.
        Email email = buscarPorId(id);
        //Usamos el metodo actualizar de la entidad email.
        email.actualizar(datos);
        //Guardamos cambios.
        repository.save(email);
    }
}
