package com.jddi.project.model.email.rel;

import com.jddi.project.model.email.Email;
import com.jddi.project.model.persona.Persona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity(name = "EmailPersona")
@Table(name = "email_persona")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EmailPersona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name = "persona_id")
    private Persona persona;
    @ManyToOne(targetEntity = Email.class)
    @JoinColumn(name = "email_id")
    private Email email;
    @JoinColumn(name = "principal")
    private Boolean principal;
    @JoinColumn(name = "activo")
    private Boolean activo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailPersona that)) return false;
        return Objects.equals(persona, that.persona) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persona, email);
    }

    public void establecerPrincipal() {
        this.principal = true;
    }

    public void desactivar(){
        this.activo = false;
    }

    public void activar(){
        this.activo = true;
    }
}
