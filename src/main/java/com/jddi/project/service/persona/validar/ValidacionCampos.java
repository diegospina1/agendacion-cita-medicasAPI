package com.jddi.project.service.persona.validar;

import com.jddi.project.model.persona.dto.ActualizarPersonaDTO;
import org.springframework.stereotype.Service;

@Service
public class ValidacionCampos {

    public void validarCamposVacios(ActualizarPersonaDTO datos){
        if(datos.nombre() != null && datos.nombre().isBlank()){
            throw new IllegalArgumentException("Nombre invalido");
        }
        if(datos.apellido() != null && datos.apellido().isBlank()){
            throw new IllegalArgumentException("Apellido invalido");
        }
    }
}
