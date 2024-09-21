package com.jddi.project.controller;

import com.jddi.project.model.doctor.dto.DatosDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.CDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.RDoctorDTO;
import com.jddi.project.model.doctor.dto.crud.UDoctorDTO;
import com.jddi.project.model.datos.contacto.email.dto.CorreosDTO;
import com.jddi.project.model.datos.contacto.email.dto.crud.EmailDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.TelefonosDTO;
import com.jddi.project.model.datos.contacto.telefono.dto.crud.TelefonoDTO;
import com.jddi.project.service.datos.persona.validar.ValidacionCampos;
import com.jddi.project.service.doctor.IDoctorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctor")
public class DoctorController {

    private final IDoctorService service;
    private final ValidacionCampos validacion;

    @Autowired
    public DoctorController(IDoctorService service, ValidacionCampos validacionCampos) {
        this.service = service;
        this.validacion = validacionCampos;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDoctorDTO> crear(@RequestBody @Valid CDoctorDTO datos){
        return ResponseEntity.ok(service.crear(datos));
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<RDoctorDTO>> listarTodos(@PathVariable("especialidad") String especialidad){
        return ResponseEntity.ok(service.listarTodosPorEspecialidad(especialidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RDoctorDTO> buscarPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.buscarId(id));
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<RDoctorDTO> buscarPorDocumento(@PathVariable("documento") String documento){
        return ResponseEntity.ok(service.buscarDocumento(documento));
    }

    @GetMapping("/detallar/{id}")
    public ResponseEntity<DatosDoctorDTO> detallarDoctor(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.detallarDoctorPorId(id));
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
    public ResponseEntity<Void> actualizar(@RequestBody @Valid UDoctorDTO datos){
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
