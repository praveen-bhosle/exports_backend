package com.example.demo.controller.userControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/user/info")
public class InfoController {

    @Autowired 
    private UserService userService ; 

    @GetMapping("")
    public ResponseEntity<?> getUserDetils() { 
        return new ResponseEntity<>(  userService.getUser()  , HttpStatus.OK) ; 
    }

}