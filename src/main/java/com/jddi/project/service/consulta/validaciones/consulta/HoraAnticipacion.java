package com.jddi.project.service.consulta.validaciones.consulta;

import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.service.consulta.validaciones.ValidadorCondiciones;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HoraAnticipacion implements ValidadorCondiciones {

    @Override
    public void validar(Consulta consulta) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime consultaFecha = consulta.getFecha_consulta();

        long diferencia = Duration.between(ahora, consultaFecha).toMinutes();

        if (diferencia < 30) {
            throw new ValidationException("Las consultas deben programarse con al menos 30 minutos de anticipación.");
        }
    }
}
