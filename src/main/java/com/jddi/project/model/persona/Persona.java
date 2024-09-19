package com.jddi.project.model.persona;

import com.jddi.project.model.email.rel.EmailPersona;
import com.jddi.project.model.persona.dto.ActualizarPersonaDTO;
import com.jddi.project.model.telefono.rel.TelefonoPersona;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Persona")
@Table(name = "personas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String documento;
    private Boolean activo;

    @Setter
    @OneToMany(mappedBy = "persona")
    private Set<EmailPersona> emails = new HashSet<>();

    @Setter
    @OneToMany(mappedBy = "persona")
    private Set<TelefonoPersona> telefonos = new HashSet<>();

    public void actualizar(ActualizarPersonaDTO datos){
        if(datos.nombre() != null) this.nombre = datos.nombre();
        if(datos.apellido() != null) this.apellido = datos.apellido();
    }

    public void desactivar(){
        this.activo = false;
    }

    public void activar(){
        this.activo = true;
    }

}
