package com.jddi.project.service.telefono;

import com.jddi.project.model.telefono.Telefono;
import com.jddi.project.model.telefono.dto.ActualizarTelefonoDTO;
import com.jddi.project.model.telefono.dto.CrearTelefonoDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;

import java.util.List;
import java.util.Optional;

public interface ITelefonoService {

    Telefono crear(CrearTelefonoDTO datos);

    List<Telefono> listarTodos();

    Telefono buscarPorId(Long id);

    Optional<Telefono> buscarPorTelefono(String telefono);

    void actualizarTelefono(Long id, ActualizarTelefonoDTO datos);

    RespuestaTelefonoDTO mappearRespuesta(Telefono telefono, Boolean activo);


}
