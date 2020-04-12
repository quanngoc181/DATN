package com.hust.datn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hust.datn.exception.Error;
import com.hust.datn.exception.InternalException;

@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Error> handleInternalException(InternalException ie) {
        com.hust.datn.exception.Error error = new com.hust.datn.exception.Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMessage(ie.getMessage());
        return new ResponseEntity<com.hust.datn.exception.Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
