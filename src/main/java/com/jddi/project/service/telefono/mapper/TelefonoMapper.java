package com.jddi.project.service.telefono.mapper;

import com.jddi.project.model.telefono.Telefono;
import com.jddi.project.model.telefono.dto.CrearTelefonoDTO;
import com.jddi.project.model.telefono.dto.RespuestaTelefonoDTO;
import org.springframework.stereotype.Service;

@Service
public class TelefonoMapper {

    public Telefono mappearTelefono(CrearTelefonoDTO datos){
        return Telefono.builder()
                .telefono(datos.telefono())
                .build();
    }

    public RespuestaTelefonoDTO mappearRespuesta(Telefono telefono, Boolean activo){
        return new RespuestaTelefonoDTO(telefono.getId(), telefono.getTelefono(), activo);
    }
}
