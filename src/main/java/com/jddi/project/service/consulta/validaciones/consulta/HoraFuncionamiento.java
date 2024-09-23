package com.jddi.project.service.consulta.validaciones.consulta;

import com.jddi.project.model.consulta.Consulta;
import com.jddi.project.service.consulta.validaciones.ValidadorCondiciones;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class HoraFuncionamiento implements ValidadorCondiciones {
    @Override
    public void validar(Consulta consulta) {

        LocalDateTime fecha = consulta.getFecha_consulta();
        int horaConsulta = fecha.getHour();

        Boolean esDomingo = DayOfWeek.SUNDAY.equals(fecha.getDayOfWeek());

        Boolean esAntesDeAbrir = (horaConsulta < 7);
        Boolean esDespuesDeCerrar = (horaConsulta > 19);

        if (esDomingo || esAntesDeAbrir || esDespuesDeCerrar){
            throw new ValidationException("El horario de atención del centro médico es de lunes a sábado de 7:00 am a 7:00 pm");
        }

    }
}
