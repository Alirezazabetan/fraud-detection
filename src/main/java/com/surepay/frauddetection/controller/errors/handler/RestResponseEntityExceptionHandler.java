package com.surepay.frauddetection.controller.errors.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        return new ResponseEntity<>("PLease Use the correct file format JSON or CSV", HttpStatus.BAD_REQUEST);
    }

}
