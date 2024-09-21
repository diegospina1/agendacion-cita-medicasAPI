package com.jddi.project.controller;

import com.jddi.project.model.consulta.dto.DatosConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.CConsultaDTO;
import com.jddi.project.service.consulta.IConsultaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("consulta")
public class ConsultaController {

    private final IConsultaService service;

    public ConsultaController(IConsultaService service) {
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosConsultaDTO> crearConsulta(@RequestBody @Valid CConsultaDTO datos, UriComponentsBuilder uriBuilder){
        DatosConsultaDTO consultaCreada = service.crear(datos);

        URI url = uriBuilder.path("/consulta/{id}")
                .buildAndExpand(consultaCreada.id()).toUri();

        return ResponseEntity.created(url).body(consultaCreada);
    }
}
