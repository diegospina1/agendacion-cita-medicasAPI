package com.jddi.project.service.paciente;

import com.jddi.project.model.email.dto.ActualizarEmailDTO;
import com.jddi.project.model.paciente.dto.*;
import com.jddi.project.model.telefono.dto.ActualizarTelefonoDTO;

import java.util.List;

public interface IPacienteService {

    DatosPacienteDTO crear(CrearPacienteDTO datos);

    List<RespuestaPacienteDTO> listarTodos();

    RespuestaPacienteDTO buscarId(Long id);

    RespuestaPacienteDTO buscarDocumento(String documento);

    DatosPacienteDTO detallarPacientePorId(Long id);

    void actualizar(ActualizarPacienteDTO datos);

    void eliminar(Long id);

    void activar(Long id);

    CorreosPacienteDTO buscarCorreosAsociados(Long id);

    TelefonosPacienteDTO buscarTelefonosAsociados(Long id);

    void eliminarTelefonoPersona(Long id, ActualizarTelefonoDTO telefono);

    void eliminarEmailPersona(Long id, ActualizarEmailDTO email);

    TelefonosPacienteDTO buscarTelefonosAsociadosActivos(Long id);

    CorreosPacienteDTO buscarCorreosAsociadosActivos(Long id);
}
