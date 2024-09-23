package com.jddi.project.controller;

import com.jddi.project.model.consulta.dto.DatosConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.RConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.UConsultaDTO;
import com.jddi.project.model.consulta.dto.crud.CConsultaDTO;
import com.jddi.project.service.consulta.IConsultaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<RConsultaDTO>> listarTodasPacienteId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.listarTodasPorPacienteId(id));
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<RConsultaDTO>> listarTodasDoctorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.listarTodasDoctorId(id));
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<RConsultaDTO>> listarTodasPorEspecialidad(@PathVariable("especialidad") String especialidad){
        return ResponseEntity.ok(service.listarTodasPorEspecialidad(especialidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosConsultaDTO> listarPorIdConsulta(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RConsultaDTO> actualizarConsulta(@RequestBody @Valid UConsultaDTO datos){
        return ResponseEntity.accepted().body(service.actualizar(datos));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> cancelarConsulta(@PathVariable("id") Long id){
        service.cancelarConsulta(id);
        return ResponseEntity.noContent().build();
    }
}
