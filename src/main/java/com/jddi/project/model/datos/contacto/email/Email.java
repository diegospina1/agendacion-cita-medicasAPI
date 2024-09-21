package com.jddi.project.model.datos.contacto.email;

import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.datos.contacto.email.rel.EmailPersona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Email")
@Table(name = "email")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @OneToMany(mappedBy = "email")
    private Set<EmailPersona> asociaciones = new HashSet<>();

    public void actualizar(EmailDTO datos) {
        this.email = datos.email();
    }
}
