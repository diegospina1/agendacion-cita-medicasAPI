package com.jddi.project.service.consulta.validaciones;

import com.jddi.project.model.consulta.Consulta;
import org.springframework.stereotype.Component;

public interface ValidadorCondiciones {
    void validar(Consulta consulta);
}
