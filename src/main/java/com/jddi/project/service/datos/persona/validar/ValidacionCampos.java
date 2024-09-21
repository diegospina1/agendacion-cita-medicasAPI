package com.jddi.project.service.datos.persona.validar;

import com.jddi.project.model.datos.persona.dto.UPersonaDTO;
import org.springframework.stereotype.Service;

@Service
public class ValidacionCampos {

    public void validarCamposVacios(UPersonaDTO datos){
        if(datos.nombre() != null && datos.nombre().isBlank()){
            throw new IllegalArgumentException("Nombre invalido");
        }
        if(datos.apellido() != null && datos.apellido().isBlank()){
            throw new IllegalArgumentException("Apellido invalido");
        }
    }
}
