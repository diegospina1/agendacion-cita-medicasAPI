package com.jddi.project.service.doctor;

import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.doctor.dto.DatosDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.CDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.RDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.UDoctorDTO;
import com.jddi.project.model.datos.contacto.email.dto.CorreosDTO;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.TelefonosDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public interface IDoctorService {
    DatosDoctorDTO crear(CDoctorDTO datos);

    List<RDoctorDTO> listarTodosPorEspecialidad(String especialidad);

    RDoctorDTO buscarId(Long id);

    RDoctorDTO buscarDocumento(String documento);

    DatosDoctorDTO detallarDoctorPorId(Long id);

    CorreosDTO buscarCorreosAsociadosActivos(Long id);

    CorreosDTO buscarCorreosAsociados(Long id);

    TelefonosDTO buscarTelefonosAsociadosActivos(Long id);

    TelefonosDTO buscarTelefonosAsociados(Long id);

    void actualizar(@Valid UDoctorDTO datos);

    void activar(Long id);

    void eliminar(Long id);

    void eliminarTelefonoPersona(Long id, @Valid TelefonoDTO telefono);

    void eliminarEmailPersona(Long id, @Valid EmailDTO email);

    //Buscar doctor por su id (para metodos de la clase y crear las consultas).
    Doctor buscarDoctorPorId(Long id);

    Doctor buscarDoctorDisponible(Especialidad especialidad, LocalDateTime fecha, LocalDateTime duracion);
}
