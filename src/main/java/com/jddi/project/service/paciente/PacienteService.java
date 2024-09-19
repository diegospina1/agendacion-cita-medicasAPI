package com.jddi.project.service.paciente;

import com.jddi.project.dao.paciente.PacienteRepository;
import com.jddi.project.model.email.dto.ActualizarEmailDTO;
import com.jddi.project.model.email.dto.CrearEmailDTO;
import com.jddi.project.model.email.dto.RespuestaEmailDTO;
import com.jddi.project.model.email.rel.EmailPersona;
import com.jddi.project.model.paciente.Paciente;
import com.jddi.project.model.paciente.dto.*;
import com.jddi.project.model.persona.Persona;
import com.jddi.project.model.telefono.dto.ActualizarTelefonoDTO;
import com.jddi.project.model.telefono.dto.CrearTelefonoDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;
import com.jddi.project.model.telefono.rel.TelefonoPersona;
import com.jddi.project.service.email.rel.IEmailPersonaService;
import com.jddi.project.service.paciente.mapper.PacienteMapper;
import com.jddi.project.service.persona.IPersonaService;
import com.jddi.project.service.telefono.rel.ITelefonoPersonaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PacienteService implements IPacienteService {

    private final PacienteRepository repository;
    private final IPersonaService service;
    private final IEmailPersonaService emailPersonaService;
    private final ITelefonoPersonaService telefonoPersonaService;
    private final PacienteMapper mapper;

    public PacienteService(PacienteRepository repository, IPersonaService service, IEmailPersonaService emailPersonaService, ITelefonoPersonaService telefonoPersonaService, PacienteMapper mapper) {
        this.repository = repository;
        this.service = service;
        this.emailPersonaService = emailPersonaService;
        this.telefonoPersonaService = telefonoPersonaService;
        this.mapper = mapper;
    }

    @Override
    public DatosPacienteDTO crear(CrearPacienteDTO datos) {
        //Creamos la persona y le asociamos el email, telefono
        Persona persona = service.crear(datos.datosPaciente());
        EmailPersona emailPersona = emailPersonaService.crear(persona, new CrearEmailDTO(datos.email()));
        TelefonoPersona telefonoPersona = telefonoPersonaService.crear(persona, new CrearTelefonoDTO(datos.telefono()));
        //Asociamos la persona a un paciente
        Paciente paciente = repository.save(new Paciente(null, persona));

        return mapper.mapearDatosPaciente(paciente, service.mapearRespuesta(paciente.getPersona()),
                emailPersonaService.mappearRespuesta(emailPersona.getEmail(), emailPersona.getActivo()),
                telefonoPersonaService.mappearRespuesta(telefonoPersona.getTelefono(), telefonoPersona.getActivo()));
    }

    @Override
    public List<RespuestaPacienteDTO> listarTodos() {
        return repository.encontrarActivos().stream()
                .map(p -> new RespuestaPacienteDTO(p.getId(), service.mapearRespuesta(p.getPersona())))
                .toList();
    }

    @Override
    public RespuestaPacienteDTO buscarId(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return new RespuestaPacienteDTO(paciente.getId(), service.mapearRespuesta(paciente.getPersona()));
    }

    @Override
    public RespuestaPacienteDTO buscarDocumento(String documento) {
        Paciente paciente = repository.findByDocumento(documento)
                .orElseThrow(EntityNotFoundException::new);
        return new RespuestaPacienteDTO(paciente.getId(), service.mapearRespuesta(paciente.getPersona()));
    }

    @Override
    public DatosPacienteDTO detallarPacientePorId(Long id) {

        Paciente paciente = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Persona persona = paciente.getPersona();

        RespuestaEmailDTO emailPaciente = emailPersonaService.buscarEmailPorPersona(persona);
        RespuestaTelefonoDTO telefonoPaciente = telefonoPersonaService.buscarTelefonoPorPersonaId(persona.getId());

        return new DatosPacienteDTO(paciente.getId(), service.mapearRespuesta(paciente.getPersona()),
                emailPaciente, telefonoPaciente);
    }

    @Override
    public void actualizar(ActualizarPacienteDTO datos) {
        Paciente paciente = repository.findById(datos.id())
                .orElseThrow(EntityNotFoundException::new);

        if (datos.datosActualizar() != null) {
            Persona persona = service.actualizar(paciente.getPersona().getId(), datos.datosActualizar());

            if (datos.datosActualizar().email() != null) {
                EmailPersona emailPersona = emailPersonaService.crear(persona,
                        new CrearEmailDTO(datos.datosActualizar().email()));
                emailPersonaService.establecerPrincipal(emailPersona);
            }
            if (datos.datosActualizar().telefono() != null) {
                TelefonoPersona telefonoPersona = telefonoPersonaService.crear(persona,
                        new CrearTelefonoDTO(datos.datosActualizar().telefono()));
                telefonoPersonaService.establecerPrincipal(telefonoPersona);
            }
        }
    }

    @Override
    public void eliminar(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        service.eliminar(paciente.getPersona().getId());
    }

    @Override
    public void activar(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        service.activar(paciente.getPersona().getId());
    }

    @Override
    public CorreosPacienteDTO buscarCorreosAsociados(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        Persona persona = paciente.getPersona();

        Set<EmailPersona> correos = emailPersonaService.buscarCorreosAsociados(persona);
        persona.setEmails(correos);

        return new CorreosPacienteDTO(paciente.getId(), service.mapearRespuesta(persona),
                persona.getEmails().stream()
                        .sorted(Comparator.comparingLong(e -> e.getEmail().getId()))
                        .map(e -> emailPersonaService.mappearRespuesta(e.getEmail(), e.getActivo()))
                        .collect(Collectors.toList()));
    }

    @Override
    public TelefonosPacienteDTO buscarTelefonosAsociados(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        Persona persona = paciente.getPersona();

        Set<TelefonoPersona> telefonos = telefonoPersonaService.buscarTelefonosAsociados(persona);
        persona.setTelefonos(telefonos);

        return new TelefonosPacienteDTO(paciente.getId(), service.mapearRespuesta(persona),
                persona.getTelefonos().stream()
                        .sorted(Comparator.comparingLong(t -> t.getTelefono().getId()))
                        .map(e -> telefonoPersonaService.mappearRespuesta(e.getTelefono(), e.getActivo()))
                        .collect(Collectors.toList()));
    }

    @Override
    public void eliminarTelefonoPersona(Long id, ActualizarTelefonoDTO telefono) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        telefonoPersonaService.eliminarRelacion(paciente.getPersona(), telefono);
    }

    @Override
    public void eliminarEmailPersona(Long id, ActualizarEmailDTO email) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        emailPersonaService.eliminarRelacion(paciente.getPersona(), email);
    }

    @Override
    public TelefonosPacienteDTO buscarTelefonosAsociadosActivos(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        Persona persona = paciente.getPersona();

        List<RespuestaTelefonoDTO> asociaciones = telefonoPersonaService.buscarTelefonosAsociadosActivos(persona);

        return new TelefonosPacienteDTO(paciente.getId(), service.mapearRespuesta(persona), asociaciones);
    }

    @Override
    public CorreosPacienteDTO buscarCorreosAsociadosActivos(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        Persona persona = paciente.getPersona();

        List<RespuestaEmailDTO> asociaciones = emailPersonaService.buscarCorreosAsociadosActivos(persona);

        return new CorreosPacienteDTO(paciente.getId(), service.mapearRespuesta(persona), asociaciones);
    }
}
