package com.jddi.project.service.doctor;

import com.jddi.project.dao.doctor.DoctorRepository;
import com.jddi.project.mapper.DoctorMapper;
import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.model.datos.contacto.email.dto.CorreosDTO;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.datos.contacto.email.rel.EmailPersona;
import com.jddi.project.model.datos.contacto.telefono.dto.TelefonosDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import com.jddi.project.model.datos.contacto.telefono.rel.TelefonoPersona;
import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.doctor.dto.DatosDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.CDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.RDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.UDoctorDTO;
import com.jddi.project.service.datos.contacto.IContactoService;
import com.jddi.project.service.datos.persona.IPersonaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService implements IDoctorService{

    private final DoctorRepository repository;
    private final DoctorMapper mapper;
    private final IPersonaService personaService;
    private final IContactoService contactoService;

    public DoctorService(DoctorRepository repository, @Qualifier("doctorMapperImpl") DoctorMapper mapper,
                         IPersonaService personaService, IContactoService contactoService) {
        this.repository = repository;
        this.mapper = mapper;
        this.personaService = personaService;
        this.contactoService = contactoService;
    }

    @Override
    public DatosDoctorDTO crear(CDoctorDTO datos) {
        try{
            Persona persona = personaService.crear(datos.datosDoctor());
            EmailPersona emailPersona = contactoService.asociarEmail(persona, new EmailDTO(datos.email()));
            TelefonoPersona telefonoPersona = contactoService.asociarTelefono(persona, new TelefonoDTO(datos.telefono()));
            Especialidad especialidad = Especialidad.valueOf(datos.especialidad().toUpperCase());

            Doctor doctor = repository.save(new Doctor(null, persona, especialidad));

            return mapper.toDoctorDTO(doctor, emailPersona.getEmail(), telefonoPersona.getTelefono());
        } catch (Exception e) {
            throw new ValidationException("Documento ya existe");
        }

    }

    @Override
    public List<RDoctorDTO> listarTodosPorEspecialidad(String especialidad) {
        Especialidad filtro = Especialidad.valueOf(especialidad.toUpperCase());

        return repository.findAllByEspecialidad(filtro).stream()
                .map(mapper::toRDoctorDTO)
                .toList();
    }

    @Override
    public RDoctorDTO buscarId(Long id) {
        Doctor doctor = buscarDoctorPorId(id);
        return mapper.toRDoctorDTO(doctor);
    }

    @Override
    public RDoctorDTO buscarDocumento(String documento) {
        Optional<Doctor> doctor = repository.findByDocumento(documento);
        if (doctor.isPresent()){
            return mapper.toRDoctorDTO(doctor.get());
        } else {
            String noEncontradoDocumento = "Doctor no encontrado para el documento: ";
            throw  new EntityNotFoundException(noEncontradoDocumento.concat(documento));
        }
    }

    @Override
    public DatosDoctorDTO detallarDoctorPorId(Long id) {
        Doctor doctor = buscarDoctorPorId(id);
        Persona persona = doctor.getPersona();

        EmailPersona emailPersona = contactoService.buscarEmailPorPersona(persona);
        TelefonoPersona telefonoPersona = contactoService.buscarTelefonoPorPersona(persona);
        return mapper.toDoctorDTO(doctor, emailPersona.getEmail(), telefonoPersona.getTelefono());
    }

    @Override
    public CorreosDTO buscarCorreosAsociadosActivos(Long id) {
        Doctor doctor = buscarDoctorPorId(id);
        Persona persona = doctor.getPersona();

        List<EmailPersona> correos = contactoService.buscarEmailsAsociadosActivos(persona);
        persona.setEmails(correos);
        return mapper.toEmailsDoctorDTO(doctor);
    }

    @Override
    public CorreosDTO buscarCorreosAsociados(Long id) {
        Doctor doctor = buscarDoctorPorId(id);
        Persona persona = doctor.getPersona();

        List<EmailPersona> correos = contactoService.buscarEmailsAsociados(persona);
        persona.setEmails(correos);
        return mapper.toEmailsDoctorDTO(doctor);
    }

    @Override
    public TelefonosDTO buscarTelefonosAsociadosActivos(Long id) {
        Doctor doctor = buscarDoctorPorId(id);
        Persona persona = doctor.getPersona();

        List<TelefonoPersona> telefonos = contactoService.buscarTelefonosAsociadosActivos(persona);
        persona.setTelefonos(telefonos);
        return mapper.toTelefonosDoctorDTO(doctor);
    }

    @Override
    public TelefonosDTO buscarTelefonosAsociados(Long id) {
        Doctor doctor = buscarDoctorPorId(id);
        Persona persona = doctor.getPersona();

        List<TelefonoPersona> telefonos = contactoService.buscarTelefonosAsociados(persona);
        persona.setTelefonos(telefonos);
        return mapper.toTelefonosDoctorDTO(doctor);
    }

    @Override
    public void actualizar(UDoctorDTO datos) {
        Doctor doctor = buscarDoctorPorId(datos.id());
        if(datos.datosActualizar() != null){
            Persona persona = personaService.actualizar(doctor.getPersona().getId(),
                    datos.datosActualizar());
            if (datos.especialidad() != null) {
                doctor.setEspecialidad(datos.especialidad());
                repository.save(doctor);
            }
            contactoService.actualizarDatosContacto(persona, datos.datosActualizar());
        }
    }

    @Override
    public void activar(Long id) {
        Doctor doctor = buscarDoctorPorId(id);
        personaService.activar(doctor.getPersona().getId());
    }

    @Override
    public void eliminar(Long id) {
        Doctor doctor = buscarDoctorPorId(id);
        personaService.eliminar(doctor.getPersona().getId());
    }

    @Override
    public void eliminarTelefonoPersona(Long id, TelefonoDTO telefono) {
        Doctor doctor = buscarDoctorPorId(id);
        contactoService.eliminarRelacionTel(doctor.getPersona(), telefono);
    }

    @Override
    public void eliminarEmailPersona(Long id, EmailDTO email) {
        Doctor doctor = buscarDoctorPorId(id);
        contactoService.eliminarRelacionEmail(doctor.getPersona(), email);
    }

    //Buscar doctor por su id (para metodos de la clase y crear las consultas).
    @Override
    public Doctor buscarDoctorPorId(Long id){
        var doctor = repository.findById(id);
        if (doctor.isPresent()) {
            return doctor.get();
        } else {
            String noEncontradoId = "Doctor no encontrado para el id: ";
            throw new EntityNotFoundException(noEncontradoId.concat(String.valueOf(id)));
        }
    }

    @Override
    public Doctor buscarDoctorDisponible(Consulta consulta) {
        Doctor doctor = repository.buscarDoctorDisponible(consulta);
        if (doctor == null){
            throw new ValidationException("no existen doctores disponibles para este horario y especialidad");
        }
        return doctor;
    }
}
