package com.example.demo.controller.publicControllers ;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AuthBody;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid; 


@RestController
@RequestMapping("/api/public/") 
class AuthController  {   

    @Autowired 
    private  UserRepository userRepository ;   

    @Autowired 
    private UserService userService ; 

    @Autowired 
    private AuthenticationManager authenticationManager ; 

    @Autowired 
    private JwtService jwtSerice; 
     
    @PostMapping("/signup") 
    public  ResponseEntity<?>  createAccount( @Valid @RequestBody  AuthBody authBody  ) {  

      User user =  userRepository.findByUsername( authBody.getUsername())  ; 
      if ( user != null  ) {    
        return   new  ResponseEntity<>(  "Username already exists."    ,    HttpStatus.CONFLICT   ) ;
      }  
      else {  
        userService.createUser(user) ; 
        return   new  ResponseEntity<>( "Account created successfully.." ,   HttpStatus.CREATED   ) ;
      }
    } 

    @PostMapping("/login")  

    public  ResponseEntity<?> login( @Valid @RequestBody AuthBody authBody ) {  

      String username = authBody.getUsername() ; 
      String passwprd = authBody.getPassword() ;  
  
    try { 
        authenticationManager.authenticate(  new UsernamePasswordAuthenticationToken(username, passwprd) )   ;     
        String token  =  jwtSerice.createToken(username) ;  
        HashMap<String,String>  responseBody   = new  HashMap<>() ;  
        responseBody.put("token" ,  token)  ;   
      return  new ResponseEntity<>(    responseBody ,     HttpStatus.ACCEPTED) ;
        } 

    catch(  BadCredentialsException e  ) { 
       return  new ResponseEntity<>( "Incorrect credentials"  ,  HttpStatus.UNAUTHORIZED   ) ;
    } 

    catch( Exception e ) { 
      return  new ResponseEntity<>( "Internal server error."  ,  HttpStatus.INTERNAL_SERVER_ERROR) ;
    } 
  }
}