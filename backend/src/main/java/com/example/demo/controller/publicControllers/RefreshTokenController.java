package com.example.demo.controller.publicControllers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.JwtService;
import com.example.demo.service.RefreshTokenService;

@RequestMapping("/api/public/refresh") 
@RestController
public class RefreshTokenController { 
 
    @Autowired 
    RefreshTokenService refreshTokenService ; 

    @Autowired 
    JwtService jwtService ; 

    @GetMapping("") 
    public ResponseEntity<?> refreshAccessToken(@CookieValue("refreshToken") String refreshToken  ) {  
        User user = refreshTokenService.validateToken(refreshToken) ; 
        if(user == null ) { 
            return new ResponseEntity<>("Refresh token expired login again." ,  HttpStatus.UNAUTHORIZED)  ; 
        }
        String accessToken =  jwtService.createToken(user.getUsername() , Instant.now().plus( 1 ,  ChronoUnit.MINUTES ) ) ; 
        HashMap<String,String> responseBody = new HashMap<>() ; 
        responseBody.put( "token" , accessToken) ; 
        return new ResponseEntity<>(  responseBody , HttpStatus.OK ) ; 
    }
}