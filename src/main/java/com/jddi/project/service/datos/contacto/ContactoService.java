package com.jddi.project.service.datos.contacto;


import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.datos.contacto.email.rel.EmailPersona;
import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.persona.dto.UPersonaDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import com.jddi.project.model.datos.contacto.telefono.rel.TelefonoPersona;
import com.jddi.project.service.datos.contacto.email.rel.IEmailPersonaService;
import com.jddi.project.service.datos.contacto.telefono.rel.ITelefonoPersonaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService implements IContactoService {

    private final IEmailPersonaService emailPersonaService;
    private final ITelefonoPersonaService telefonoPersonaService;

    public ContactoService(IEmailPersonaService emailPersonaService, ITelefonoPersonaService telefonoPersonaService) {
        this.emailPersonaService = emailPersonaService;
        this.telefonoPersonaService = telefonoPersonaService;
    }

    //Email
    @Override
    public EmailPersona asociarEmail(Persona persona, EmailDTO email) {
        return emailPersonaService.asociarEmail(persona, email);
    }

    @Override
    public void establecerPrincipalEmail(EmailPersona emailPersona) {
        emailPersonaService.establecerPrincipalEmail(emailPersona);
    }

    @Override
    public EmailPersona buscarEmailPorPersona(Persona persona) {
        return emailPersonaService.buscarEmailPorPersona(persona);
    }

    @Override
    public List<EmailPersona> buscarEmailsAsociadosActivos(Persona persona) {
        return emailPersonaService.buscarEmailsAsociadosActivos(persona);
    }

    @Override
    public List<EmailPersona> buscarEmailsAsociados(Persona persona) {
        return emailPersonaService.buscarEmailsAsociados(persona);
    }

    @Override
    public void eliminarRelacionEmail(Persona persona, EmailDTO email) {
        emailPersonaService.eliminarRelacionEmail(persona, email);
    }

    //Telefono
    @Override
    public TelefonoPersona asociarTelefono(Persona persona, TelefonoDTO datos) {
        return telefonoPersonaService.asociarTelefono(persona, datos);
    }

    @Override
    public void establecerPrincipalTel(TelefonoPersona asociacion) {
        telefonoPersonaService.establecerPrincipalTel(asociacion);
    }

    @Override
    public TelefonoPersona buscarTelefonoPorPersona(Persona persona) {
        return telefonoPersonaService.buscarTelefonoPorPersona(persona);
    }

    @Override
    public List<TelefonoPersona> buscarTelefonosAsociadosActivos(Persona persona) {
        return telefonoPersonaService.buscarTelefonosAsociadosActivos(persona);
    }

    @Override
    public List<TelefonoPersona> buscarTelefonosAsociados(Persona persona) {
        return telefonoPersonaService.buscarTelefonosAsociados(persona);
    }

    @Override
    public void eliminarRelacionTel(Persona persona, TelefonoDTO telefono) {
        telefonoPersonaService.eliminarRelacionTel(persona, telefono);
    }

    @Override
    public void actualizarDatosContacto(Persona persona, UPersonaDTO datos) {
        if (datos.email() != null) {
            EmailPersona emailPersona = emailPersonaService.asociarEmail(persona,
                    new EmailDTO(datos.email()));
            emailPersonaService.establecerPrincipalEmail(emailPersona);
        }
        if (datos.telefono() != null) {
            TelefonoPersona telefonoPersona = telefonoPersonaService.asociarTelefono(persona,
                    new TelefonoDTO(datos.telefono()));
            telefonoPersonaService.establecerPrincipalTel(telefonoPersona);
        }
    }
}
