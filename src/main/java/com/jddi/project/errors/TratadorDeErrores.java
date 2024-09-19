package com.jddi.project.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity errorDeCliente404(EntityNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity errorValidacionDatos(MethodArgumentNotValidException e){
        List<DatosErrorValidacion> errores = e.getFieldErrors().stream()
                .map(this::mappearDatosError).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errores);
    }

    //Crear el DTO que se presentará al cliente
    public DatosErrorValidacion mappearDatosError(FieldError error){
        return new DatosErrorValidacion(error.getField(), error.getDefaultMessage());
    }

}
