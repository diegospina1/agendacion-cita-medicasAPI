package com.jddi.project.model.datos.contacto.telefono.rel;

import com.jddi.project.model.datos.persona.Persona;
import com.jddi.project.model.datos.contacto.telefono.Telefono;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity(name = "TelefonoPersona")
@Table(name = "telefono_persona")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TelefonoPersona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name = "persona_id")
    private Persona persona;
    @ManyToOne(targetEntity = Telefono.class)
    @JoinColumn(name = "telefono_id")
    private Telefono telefono;
    @JoinColumn(name = "principal")
    private Boolean principal;
    @JoinColumn(name = "activo")
    private Boolean activo;

    public void actualizar(Persona persona, Telefono telefono){
        if (persona != null) this.persona = persona;
        if (telefono != null) this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelefonoPersona that)) return false;
        return Objects.equals(persona, that.persona) && Objects.equals(telefono, that.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persona, telefono);
    }

    public void establecerPrincipal(){
        this.principal = true;
    }

    public void desactivar(){
        this.activo = false;
    }

    public void activar(){
        this.activo = true;
    }
}
