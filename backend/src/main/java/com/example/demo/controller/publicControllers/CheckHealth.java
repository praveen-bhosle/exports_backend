package com.example.demo.controller.publicControllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckHealth {
    
    @GetMapping("/api/public/checkHealth")  

    public ResponseEntity<?> health() { 
      return   new ResponseEntity<>(   HttpStatus.ACCEPTED   ) ; 
    } 

}
