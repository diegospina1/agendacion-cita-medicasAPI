package com.jddi.project.service.datos.contacto.email.rel;

import com.jddi.project.dao.email.rel.EmailPersonaRepository;
import com.jddi.project.mapper.datos.ContactoMapper;
import com.jddi.project.model.datos.contacto.email.Email;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.datos.contacto.email.rel.EmailPersona;
import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.service.datos.contacto.email.IEmailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EmailPersonaService implements IEmailPersonaService{

    private final EmailPersonaRepository repository;
    private final IEmailService emailService;
    private final ContactoMapper mapper;

    @Autowired
    public EmailPersonaService(EmailPersonaRepository repository,
                               @Qualifier("contactoMapperImpl") ContactoMapper mapper, IEmailService emailService) {
        this.repository = repository;
        this.mapper = mapper;
        this.emailService = emailService;
    }

    @Override
    public EmailPersona asociarEmail(Persona persona, EmailDTO email){
        //Service de Email para crear
        Email emailNuevo = emailService.crear(email);
        //Creamos nuestra asociacion con los argumentos del metodo
        EmailPersona emailPersona = mapper.toEmailPersona(persona, emailNuevo);
        //Buscamos todos los emails asociados de la persona en BD
        List<EmailPersona> asociaciones = repository.findAllByPersona(persona);

        //Iteramos la lista de emails asociados para ver si la relacion ya existe
        for (EmailPersona asociacion : asociaciones) {
            //Si existe, lo activa, lo guarda en BD y lo retorna.
            if (asociacion.equals(emailPersona)) {
                asociacion.activar();
                return repository.save(asociacion);
            }
        }
        //Si no existe, lo crea y lo marca como principal
        emailPersona.establecerPrincipal();
        return repository.save(emailPersona);
    }

    @Override
    public void establecerPrincipalEmail(EmailPersona asociacion){
        asociacion.establecerPrincipal();
        repository.save(asociacion);
        repository.desactivarOtros(asociacion);
    }

    @Override
    public EmailPersona buscarEmailPorPersona(Persona persona) {
        return repository.encontrarPrincipal(persona);
    }

    @Override
    public List<EmailPersona> buscarEmailsAsociadosActivos(Persona persona) {
        //Recuperamos la lista de emails ACTIVOS en BD.
        List<EmailPersona> emailsActivos = repository.encontrarActivos(persona);
        //Retornamos una lista ordenada de menor a mayor, teniendo en cuenta el "id" del email.
        return emailsActivos.stream()
                .sorted(Comparator.comparingLong(ep -> ep.getEmail().getId()))
                .toList();
    }

    @Override
    public List<EmailPersona> buscarEmailsAsociados(Persona persona) {
        //Recupera la lista de todos los emails asociados, activos o no.
        List<EmailPersona> asociaciones = repository.findAllByPersona(persona);
        return asociaciones.stream()
                .sorted(Comparator.comparingLong(ep -> ep.getEmail().getId()))
                .toList();
    }

    @Override
    public void eliminarRelacionEmail(Persona persona, EmailDTO datos) {
        //Verificamos que exista el email.
        Email email = emailService.buscarPorEmail(datos.email());
        //Verificamos que exista la asociacion
        EmailPersona emailPersona = repository.buscarAsociacion(persona, email)
                .orElseThrow(EntityNotFoundException::new);

        //Desactivamos (eliminamos) la asociacion de la persona con el email.
        emailPersona.desactivar();
        repository.save(emailPersona);
    }
}
