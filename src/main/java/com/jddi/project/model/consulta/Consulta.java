package com.jddi.project.model.consulta;

import com.jddi.project.model.doctor.Doctor;
import com.jddi.project.model.doctor.Especialidad;
import com.jddi.project.model.paciente.Paciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @ManyToOne(targetEntity = Doctor.class)
    private Doctor doctor;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    private LocalDateTime fecha_consulta;
    @JoinColumn(name = "duracion")
    private LocalDateTime duracion;
    private LocalDateTime fecha_creacion;
}
