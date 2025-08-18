package com.ecom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MyMethodArgumentNotValidException(MethodArgumentNotValidException e){

        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->{
                    String  field = error.getField();
                    String message = error.getDefaultMessage();
                    response.put(field, message);
                }

        );


        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}
