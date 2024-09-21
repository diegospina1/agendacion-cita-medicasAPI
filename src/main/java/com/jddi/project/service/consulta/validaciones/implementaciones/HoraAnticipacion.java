package com.jddi.project.service.consulta.validaciones.implementaciones;

import com.jddi.project.model.consulta.dto.crud.CConsultaDTO;
import com.jddi.project.service.consulta.validaciones.ValidadorConsulta;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HoraAnticipacion implements ValidadorConsulta {

    @Override
    public void validarConsulta(CConsultaDTO datosConsulta) {
        //Hora actual
        LocalDateTime ahora = LocalDateTime.now();
        //Hora seleccionada para la consulta
        LocalDateTime fechaConsulta = datosConsulta.fecha();

        //Validar que no este siendo agendada para a menos de 30 minutos
        Boolean diferencia30Min = (Duration.between(ahora, fechaConsulta).toMinutes() < 30);

        if (diferencia30Min) { //Si la diferencia es menor a 30 min, lanzamos una exepcion
            throw new ValidationException("Las consultas deben programarse con al menos 30 minutos de anticipacion.");
        }
    }
}
