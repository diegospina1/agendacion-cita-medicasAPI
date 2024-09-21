package com.jddi.project.model.doctor;

import com.jddi.project.model.datos.persona.Persona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Doctor")
@Table(name = "doctores")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @Setter
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

}
