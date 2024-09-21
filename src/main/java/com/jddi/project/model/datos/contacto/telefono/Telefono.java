package com.jddi.project.model.datos.contacto.telefono;

import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import com.jddi.project.model.datos.contacto.telefono.rel.TelefonoPersona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Telefono")
@Table(name = "telefonos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telefono;

    @OneToMany(mappedBy = "telefono")
    private Set<TelefonoPersona> asociaciones = new HashSet<>();

    public void actualizar(TelefonoDTO datos){
        this.telefono = datos.telefono();
    }
}
