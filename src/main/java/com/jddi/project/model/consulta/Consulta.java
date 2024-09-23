package com.jddi.project.model.consulta;

import com.jddi.project.model.consulta.dto.crud.UConsultaDTO;
import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.paciente.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Entity(name = "Consulta")
@Table(name = "consultas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Paciente.class)
    private Paciente paciente;
    @Setter
    @ManyToOne(targetEntity = Doctor.class)
    private Doctor doctor;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    private LocalDateTime fecha_consulta;
    @JoinColumn(name = "duracion")
    private LocalDateTime duracion;
    private LocalDateTime fecha_creacion;
    private Boolean cancelada;
    private LocalDateTime ultima_modificacion;

    public Consulta(Paciente paciente, Especialidad especialidad, LocalDateTime fecha_consulta) {
        this.paciente = paciente;
        this.especialidad = especialidad;
        this.fecha_consulta = fecha_consulta;
        this.duracion = fecha_consulta.plus(15, TimeUnit.MINUTES.toChronoUnit());
        this.fecha_creacion = LocalDateTime.now();
        this.cancelada = false;
        this.ultima_modificacion = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consulta consulta)) return false;
        return Objects.equals(paciente, consulta.paciente) && Objects.equals(doctor, consulta.doctor) && especialidad == consulta.especialidad && Objects.equals(fecha_consulta, consulta.fecha_consulta) && Objects.equals(duracion, consulta.duracion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paciente, doctor, especialidad, fecha_consulta, duracion);
    }

    public void actualizar(LocalDateTime fecha) {
        if (fecha != null) {
            this.fecha_consulta = fecha;
            this.duracion = fecha.plus(15, TimeUnit.MINUTES.toChronoUnit());
            this.ultima_modificacion = LocalDateTime.now();
        }
    }

    public void cancelar(){
        this.cancelada = true;
        this.ultima_modificacion = LocalDateTime.now();
    }

    public void activar(){
        this.cancelada = false;
        this.ultima_modificacion = LocalDateTime.now();
    }
}
