package com.ecom.exceptions;

import com.ecom.payload.ApiResponse;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){

        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->{
                    String  field = error.getField();
                    String message = error.getDefaultMessage();
                    response.put(field, message);
                }

        );


        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> myResourceNotFoundException(ResourceNotFoundException e){

        String message = e.getMessage();
        ApiResponse response = new ApiResponse(message, false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse > myApiException(ApiException e){
        String message = e.getMessage();
        ApiResponse response = new ApiResponse(message, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
