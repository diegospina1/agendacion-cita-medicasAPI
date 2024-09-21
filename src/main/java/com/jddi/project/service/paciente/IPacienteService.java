package com.jddi.project.service.paciente;

import com.jddi.project.model.datos.contacto.email.dto.CorreosDTO;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.paciente.Paciente;
import com.jddi.project.model.paciente.dto.*;
import com.jddi.project.model.paciente.dto.crud.UPacienteDTO;
import com.jddi.project.model.paciente.dto.crud.CPacienteDTO;
import com.jddi.project.model.paciente.dto.crud.RPacienteDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.TelefonosDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;

import java.util.List;

public interface IPacienteService {

    DatosPacienteDTO crear(CPacienteDTO datos);

    List<RPacienteDTO> listarTodos();

    RPacienteDTO buscarId(Long id);

    RPacienteDTO buscarDocumento(String documento);

    DatosPacienteDTO detallarPacientePorId(Long id);

    void actualizar(UPacienteDTO datos);

    void eliminar(Long id);

    void activar(Long id);

    CorreosDTO buscarCorreosAsociados(Long id);

    TelefonosDTO buscarTelefonosAsociados(Long id);

    void eliminarTelefonoPersona(Long id, TelefonoDTO telefono);

    void eliminarEmailPersona(Long id, EmailDTO email);

    TelefonosDTO buscarTelefonosAsociadosActivos(Long id);

    CorreosDTO buscarCorreosAsociadosActivos(Long id);

    //Buscar paciente por su id (para metodos de la clase y crear las consultas)
    Paciente buscarPacientePorId(Long id);
}
