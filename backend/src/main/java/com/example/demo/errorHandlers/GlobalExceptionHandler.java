package com.example.demo.errorHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> InvalidMethodArguments(MethodArgumentNotValidException e )  {  
        return  new ResponseEntity<>("Invalid arguments passed in the body." ,  HttpStatus.BAD_REQUEST  ) ;    
    }  
}