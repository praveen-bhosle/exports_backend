package com.example.messaging_rabbitmq.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {

    @GetMapping("")
    public ResponseEntity<?> test() { 
        return new ResponseEntity<>( "Hi there" , HttpStatus.OK )  ; 
    }
    
}
