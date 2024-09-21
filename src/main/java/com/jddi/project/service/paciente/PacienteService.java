package com.jddi.project.service.paciente;

import com.jddi.project.dao.paciente.PacienteRepository;
import com.jddi.project.mapper.PacienteMapper;
import com.jddi.project.model.datos.contacto.email.dto.CorreosDTO;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.datos.contacto.email.rel.EmailPersona;
import com.jddi.project.model.paciente.Paciente;
import com.jddi.project.model.paciente.dto.DatosPacienteDTO;
import com.jddi.project.model.paciente.dto.crud.UPacienteDTO;
import com.jddi.project.model.paciente.dto.crud.CPacienteDTO;
import com.jddi.project.model.paciente.dto.crud.RPacienteDTO;
import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.contacto.telefono.dto.TelefonosDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import com.jddi.project.model.datos.contacto.telefono.rel.TelefonoPersona;
import com.jddi.project.service.datos.contacto.IContactoService;
import com.jddi.project.service.datos.persona.IPersonaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    private final PacienteRepository repository;
    private final PacienteMapper mapper;
    private final IPersonaService personaService;
    private final IContactoService contactoService;

    public PacienteService(PacienteRepository repository, IPersonaService service,
                           @Qualifier("pacienteMapperImpl") PacienteMapper mapper, IContactoService contactoService) {
        this.repository = repository;
        this.mapper = mapper;
        this.personaService = service;
        this.contactoService = contactoService;
    }

    //CRUD
    @Override
    public DatosPacienteDTO crear(CPacienteDTO datos) {
        //Creamos la persona y le asociamos el email, telefono
        Persona persona = personaService.crear(datos.datosPaciente());
        EmailPersona emailPersona = contactoService.asociarEmail(persona, new EmailDTO(datos.email()));
        TelefonoPersona telefonoPersona = contactoService.asociarTelefono(persona, new TelefonoDTO(datos.telefono()));
        //Asociamos la persona a un paciente
        Paciente paciente = repository.save(new Paciente(null, persona));
        //retornamos un DTO con la respuesta del Paciente creado
        return mapper.toPacienteDTO(paciente, emailPersona.getEmail(), telefonoPersona.getTelefono());
    }

    @Override
    public List<RPacienteDTO> listarTodos() {
        return repository.encontrarActivos().stream()
                .map(mapper::toRPacienteDTO)
                .toList();
    }

    @Override
    public RPacienteDTO buscarId(Long id) {
        Paciente paciente = buscarPacientePorId(id);
        return mapper.toRPacienteDTO(paciente);
    }

    @Override
    public RPacienteDTO buscarDocumento(String documento) {
        Optional<Paciente> paciente = repository.findByDocumento(documento);
        if (paciente.isPresent()){
            return mapper.toRPacienteDTO(paciente.get());
        } else {
            String noEncontradoDocumento = "Paciente no encontrado para el documento: ";
            throw new EntityNotFoundException(noEncontradoDocumento.concat(documento));
        }
    }

    @Override
    public DatosPacienteDTO detallarPacientePorId(Long id) {
        Paciente paciente = buscarPacientePorId(id);
        Persona persona = paciente.getPersona();

        EmailPersona emailPrincipal = contactoService.buscarEmailPorPersona(persona);
        TelefonoPersona telefonoPrincipal = contactoService.buscarTelefonoPorPersona(persona);

        return mapper.toPacienteDTO(paciente, emailPrincipal.getEmail(), telefonoPrincipal.getTelefono());
    }

    @Override
    public void actualizar(UPacienteDTO datos) {
        Paciente paciente = buscarPacientePorId(datos.id());
        if (datos.datosActualizar() != null) {
            Persona persona = personaService.actualizar(paciente.getPersona().getId(),
                    datos.datosActualizar());

            contactoService.actualizarDatosContacto(persona, datos.datosActualizar());
        }
    }

    //Desactivar o activar Paciente
    @Override
    public void eliminar(Long id) {
        Paciente paciente = buscarPacientePorId(id);
        personaService.eliminar(paciente.getPersona().getId());
    }

    @Override
    public void activar(Long id) {
        Paciente paciente = buscarPacientePorId(id);
        personaService.activar(paciente.getPersona().getId());
    }

    //Toda la información de contacto asociada, aunque ya no esté activa
    @Override
    public CorreosDTO buscarCorreosAsociados(Long id) {
        Paciente paciente = buscarPacientePorId(id);
        Persona persona = paciente.getPersona();

        List<EmailPersona> correos = contactoService.buscarEmailsAsociados(persona);
        persona.setEmails(correos);

        return mapper.toEmailsPacienteDTO(paciente);
    }

    @Override
    public TelefonosDTO buscarTelefonosAsociados(Long id) {
        Paciente paciente = buscarPacientePorId(id);
        Persona persona = paciente.getPersona();

        List<TelefonoPersona> telefonos = contactoService.buscarTelefonosAsociados(persona);
        persona.setTelefonos(telefonos);

        return mapper.toTelefonosPacienteDTO(paciente);
    }

    //Toda la información de contacto asociada activa
    @Override
    public TelefonosDTO buscarTelefonosAsociadosActivos(Long id) {
        Paciente paciente = buscarPacientePorId(id);
        Persona persona = paciente.getPersona();

        List<TelefonoPersona> asociaciones = contactoService.buscarTelefonosAsociadosActivos(persona);
        persona.setTelefonos(asociaciones);

        return mapper.toTelefonosPacienteDTO(paciente);
    }

    @Override
    public CorreosDTO buscarCorreosAsociadosActivos(Long id) {
        Paciente paciente = buscarPacientePorId(id);
        Persona persona = paciente.getPersona();

        List<EmailPersona> asociaciones = contactoService.buscarEmailsAsociadosActivos(persona);
        persona.setEmails(asociaciones);

        return mapper.toEmailsPacienteDTO(paciente);
    }

    //Eliminar información de contacto, telefono o correo.
    @Override
    public void eliminarTelefonoPersona(Long id, TelefonoDTO telefono) {
        Paciente paciente = buscarPacientePorId(id);
        contactoService.eliminarRelacionTel(paciente.getPersona(), telefono);
    }

    @Override
    public void eliminarEmailPersona(Long id, EmailDTO email) {
        Paciente paciente = buscarPacientePorId(id);

        contactoService.eliminarRelacionEmail(paciente.getPersona(), email);
    }

    //Buscar paciente por su id (para metodos de la clase y crear las consultas)
    @Override
    public Paciente buscarPacientePorId(Long id){
        Optional<Paciente> paciente = repository.findById(id);
        if (paciente.isPresent()){
            return paciente.get();
        } else {
            String noEncontradoId = "Paciente no encontrado para el id: ";
            throw new EntityNotFoundException(noEncontradoId.concat(String.valueOf(id)));
        }
    }
}
