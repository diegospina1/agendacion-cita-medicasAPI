package com.jddi.project.model.datos.persona;

import com.jddi.project.model.datos.contacto.email.rel.EmailPersona;
import com.jddi.project.model.datos.persona.dto.UPersonaDTO;
import com.jddi.project.model.datos.contacto.telefono.rel.TelefonoPersona;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private List<EmailPersona> emails;

    @Setter
    @OneToMany(mappedBy = "persona")
    private List<TelefonoPersona> telefonos;

    public void actualizar(UPersonaDTO datos) {
        if (datos.nombre() != null) this.nombre = datos.nombre();
        if (datos.apellido() != null) this.apellido = datos.apellido();
    }

    public void desactivar() {
        this.activo = false;
    }

    public void activar() {
        this.activo = true;
    }

}
