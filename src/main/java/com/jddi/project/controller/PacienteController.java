package com.jddi.project.controller;

import com.jddi.project.model.email.dto.ActualizarEmailDTO;
import com.jddi.project.model.paciente.dto.*;
import com.jddi.project.model.telefono.dto.ActualizarTelefonoDTO;
import com.jddi.project.service.paciente.IPacienteService;
import com.jddi.project.service.paciente.PacienteService;
import com.jddi.project.service.persona.validar.ValidacionCampos;
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
    public ResponseEntity<DatosPacienteDTO> crear(@RequestBody CrearPacienteDTO datos){
        return ResponseEntity.ok(service.crear(datos));
    }

    @GetMapping
    public ResponseEntity<List<RespuestaPacienteDTO>> listarTodos(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaPacienteDTO> buscarPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarId(id));
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<RespuestaPacienteDTO> buscarPorDocumento(@PathVariable("documento") String documento){
        return ResponseEntity.ok(service.buscarDocumento(documento));
    }

    @GetMapping("/detallar/{id}")
    public ResponseEntity<DatosPacienteDTO> detallarPaciente(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.detallarPacientePorId(id));
    }

    @GetMapping("/{id}/correos")
    public ResponseEntity<CorreosPacienteDTO> correosAsociadosActivos(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarCorreosAsociadosActivos(id));
    }

    @GetMapping("/{id}/correos/todos")
    public ResponseEntity<CorreosPacienteDTO> correosAsociados(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarCorreosAsociados(id));
    }

    @GetMapping("/{id}/telefonos")
    public ResponseEntity<TelefonosPacienteDTO> telefonosAsociadosActivos(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarTelefonosAsociadosActivos(id));
    }

    @GetMapping("/{id}/telefonos/todos")
    public ResponseEntity<TelefonosPacienteDTO> telefonosAsociados(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarTelefonosAsociados(id));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Void> actualizar(@RequestBody @Valid ActualizarPacienteDTO datos){
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
    public ResponseEntity<Void> eliminarTelefonoAsociado(@PathVariable("id") Long id, @RequestBody @Valid ActualizarTelefonoDTO telefono){
        service.eliminarTelefonoPersona(id, telefono);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/correos")
    @Transactional
    public ResponseEntity<Void> eliminarCorreoAsociado(@PathVariable("id") Long id, @RequestBody @Valid ActualizarEmailDTO email){
        service.eliminarEmailPersona(id, email);
        return ResponseEntity.noContent().build();
    }
}
