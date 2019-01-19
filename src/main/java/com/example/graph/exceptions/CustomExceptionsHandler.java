package com.example.graph.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CustomExceptionsHandler {
    @ExceptionHandler(IncorrectFileContentException.class)
    public final ResponseEntity IncorrectFileContentExceptionHandler() {
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }
}
