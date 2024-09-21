package com.jddi.project.service.datos.contacto.telefono;

import com.jddi.project.model.datos.contacto.telefono.Telefono;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;

public interface ITelefonoService {

    Telefono crear(TelefonoDTO datos);

    Telefono buscarPorTelefono(String telefono);

    Telefono buscarPorId(Long id);

    void actualizarTelefono(Long id, TelefonoDTO datos);

}
