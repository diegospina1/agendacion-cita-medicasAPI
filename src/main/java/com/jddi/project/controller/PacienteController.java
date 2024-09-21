package com.jddi.project.controller;

import com.jddi.project.model.datos.contacto.email.dto.CorreosDTO;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.paciente.dto.*;
import com.jddi.project.model.paciente.dto.crud.UPacienteDTO;
import com.jddi.project.model.paciente.dto.crud.CPacienteDTO;
import com.jddi.project.model.paciente.dto.crud.RPacienteDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.TelefonosDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import com.jddi.project.service.paciente.IPacienteService;
import com.jddi.project.service.datos.persona.validar.ValidacionCampos;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("paciente")
public class PacienteController {

    private final IPacienteService service;
    private final ValidacionCampos validacion;

    @Autowired
    public PacienteController(IPacienteService service, ValidacionCampos validacion) {
        this.service = service;
        this.validacion = validacion;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosPacienteDTO> crear(@RequestBody @Valid CPacienteDTO datos){
        return ResponseEntity.ok(service.crear(datos));
    }

    @GetMapping
    public ResponseEntity<List<RPacienteDTO>> listarTodos(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RPacienteDTO> buscarPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarId(id));
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<RPacienteDTO> buscarPorDocumento(@PathVariable("documento") String documento){
        return ResponseEntity.ok(service.buscarDocumento(documento));
    }

    @GetMapping("/detallar/{id}")
    public ResponseEntity<DatosPacienteDTO> detallarPaciente(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.detallarPacientePorId(id));
    }

    @GetMapping("/{id}/correos")
    public ResponseEntity<CorreosDTO> correosAsociadosActivos(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarCorreosAsociadosActivos(id));
    }

    @GetMapping("/{id}/correos/todos")
    public ResponseEntity<CorreosDTO> correosAsociados(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarCorreosAsociados(id));
    }

    @GetMapping("/{id}/telefonos")
    public ResponseEntity<TelefonosDTO> telefonosAsociadosActivos(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarTelefonosAsociadosActivos(id));
    }

    @GetMapping("/{id}/telefonos/todos")
    public ResponseEntity<TelefonosDTO> telefonosAsociados(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarTelefonosAsociados(id));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> actualizar(@RequestBody @Valid UPacienteDTO datos){
        validacion.validarCamposVacios(datos.datosActualizar());
        service.actualizar(datos);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/activar/{id}")
    @Transactional
    public ResponseEntity<Void> activar(@PathVariable("id") Long id){
        service.activar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/telefonos")
    @Transactional
    public ResponseEntity<Void> eliminarTelefonoAsociado(@PathVariable("id") Long id, @RequestBody @Valid TelefonoDTO telefono){
        service.eliminarTelefonoPersona(id, telefono);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/correos")
    @Transactional
    public ResponseEntity<Void> eliminarCorreoAsociado(@PathVariable("id") Long id, @RequestBody @Valid EmailDTO email){
        service.eliminarEmailPersona(id, email);
        return ResponseEntity.noContent().build();
    }
}
