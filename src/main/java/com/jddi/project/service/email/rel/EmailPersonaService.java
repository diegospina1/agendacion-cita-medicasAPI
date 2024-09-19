package com.jddi.project.service.email.rel;

import com.jddi.project.dao.email.rel.EmailPersonaRepository;
import com.jddi.project.model.email.Email;
import com.jddi.project.model.email.dto.ActualizarEmailDTO;
import com.jddi.project.model.email.dto.CrearEmailDTO;
import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.email.rel.EmailPersona;
import com.jddi.project.model.persona.Persona;
import com.jddi.project.service.email.IEmailService;
import com.jddi.project.service.email.mapper.EmailPersonaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmailPersonaService implements IEmailPersonaService{

    private final EmailPersonaRepository repository;
    private final EmailPersonaMapper mapper;
    private final IEmailService emailService;

    @Autowired
    public EmailPersonaService(EmailPersonaRepository repository, EmailPersonaMapper mapper, IEmailService emailService) {
        this.repository = repository;
        this.mapper = mapper;
        this.emailService = emailService;
    }

    public EmailPersona crear(Persona persona, CrearEmailDTO email){
        Email emailNuevo = emailService.crear(email);
        //De aca para abajo es para validar si la asociación ya existe
        List<EmailPersona> asociaciones = repository.findAllByPersona(persona);
        EmailPersona emailPersona = mapper.mappearEmailPersona(persona, emailNuevo);

        for (EmailPersona asociacion : asociaciones) {
            if (asociacion.equals(emailPersona)) {
                asociacion.activar();
                return repository.save(asociacion);
            }
        }
        emailPersona.establecerPrincipal();
        return repository.save(emailPersona);
    }

    public void establecerPrincipal(EmailPersona emailPersona){
        repository.setPrincipalEmail(emailPersona);
    }

    public RespuestaEmailDTO mappearRespuesta(Email email, Boolean activo){
        return emailService.mappearRespuesta(email, activo);
    }

    @Override
    public RespuestaEmailDTO buscarEmailPorPersona(Persona persona) {
        EmailPersona emailPersona = repository.encontrarPrincipal(persona);
        return mappearRespuesta(emailPersona.getEmail(), emailPersona.getActivo());
    }

    @Override
    public List<RespuestaEmailDTO> buscarCorreosAsociadosActivos(Persona persona) {
        List<EmailPersona> emails = repository.encontrarActivos(persona);
        return emails.stream()
                .map(e -> new RespuestaEmailDTO(e.getEmail().getId(), e.getEmail().getEmail(), e.getActivo()))
                .sorted(Comparator.comparingLong(RespuestaEmailDTO::id))
                .collect(Collectors.toList());
    }

    @Override
    public Set<EmailPersona> buscarCorreosAsociados(Persona persona) {
        return new HashSet<>(repository.findAllByPersona(persona));
    }

    @Override
    public void eliminarRelacion(Persona persona, ActualizarEmailDTO datos) {
        Email email = emailService.buscarPorEmail(datos.email());

        EmailPersona emailPersona = repository.buscarAsociacion(persona, email)
                .orElseThrow(EntityNotFoundException::new);

        emailPersona.desactivar();
        repository.save(emailPersona);
    }
}
