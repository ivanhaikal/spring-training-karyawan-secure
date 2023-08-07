package com.idstar.karyawan.controller;

import com.idstar.karyawan.dao.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GeneralResponse<String>> constraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(new GeneralResponse<>(HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<GeneralResponse<String>> responseStatusException(ResponseStatusException exception) {
        return new ResponseEntity<>(new GeneralResponse<>(exception.getStatus().value(),
                exception.getReason(), exception.getStatus().getReasonPhrase()), exception.getStatus());
    }
}
