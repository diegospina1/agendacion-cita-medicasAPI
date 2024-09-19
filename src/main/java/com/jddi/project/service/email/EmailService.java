package com.jddi.project.service.email;

import com.jddi.project.dao.email.EmailRepository;
import com.jddi.project.model.email.Email;
import com.jddi.project.model.email.dto.ActualizarEmailDTO;
import com.jddi.project.model.email.dto.CrearEmailDTO;
import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.service.email.mapper.EmailMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService implements IEmailService{

    private final EmailRepository repository;
    private final EmailMapper mapper;

    @Autowired
    public EmailService(EmailRepository repository, EmailMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Email crear(CrearEmailDTO datos){
        Optional<Email> buscarEmail = repository.findByEmail(datos.email());
        return buscarEmail.orElseGet(() -> repository.save(mapper.mappearEmail(datos)));
    }

    @Override
    public Email buscarPorEmail(String email){
        return repository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Email buscarPorId(Long id){
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void actualizar(Long id, ActualizarEmailDTO datos){
        Email email = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        email.actualizar(datos);
        repository.save(email);
    }

    @Override
    public RespuestaEmailDTO mappearRespuesta(Email email, Boolean activo) {
        return mapper.mappearRespesta(email, activo);
    }
}
