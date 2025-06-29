package com.example.demo.controller.publicControllers ;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AuthBody;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import com.example.demo.service.RefreshTokenService;
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

    @Autowired
    private RefreshTokenService refreshTokenService; 
     
    @PostMapping("/signup") 
    public  ResponseEntity<?>  createAccount( @Valid @RequestBody  AuthBody authBody  ) {   

      System.out.println("Creating account.");  

      System.out.print("Username from body  " + authBody.getUsername());
    
        
      try { 
      User user =  userRepository.findByUsername( authBody.getUsername())  ;  

      System.out.println(user);
      if ( user != null  ) {    
        return   new  ResponseEntity<>(  "Username already exists."    ,    HttpStatus.CONFLICT   ) ;
      }  
      else {  
        System.out.println("Creating user ");
        userService.createUser( authBody ) ; 
        return   new  ResponseEntity<>( "Account created successfully.." ,   HttpStatus.CREATED   ) ;
      } 
      } 

      catch( Exception e) { 
        System.err.println(e); 
        return new ResponseEntity<>("Internal server error." , HttpStatus.INTERNAL_SERVER_ERROR) ; 
      }
     }  

    @PostMapping("/login")  

    public  ResponseEntity<?> login( @Valid @RequestBody AuthBody authBody ) {  

      String username = authBody.getUsername() ; 
      String password = authBody.getPassword() ;  
  
    try { 
        authenticationManager.authenticate(  new UsernamePasswordAuthenticationToken(username, password) )   ;     
        String accessToken  =  jwtSerice.createToken(username , Instant.now().plus( 1 , ChronoUnit.MINUTES )) ;  
        User user  = userService.getUserByUsername(username) ; 
        RefreshToken refreshToken =  refreshTokenService.createToken(user) ; 
        ResponseCookie cookie = ResponseCookie.from("refreshToken" , refreshToken.getToken()) 
                                               .httpOnly(true) 
                                               .path("/api/public/refresh")
                                               .maxAge(Duration.ofDays(7))
                                               .build() ; 
        HttpHeaders headers = new HttpHeaders() ; 
        headers.add("Set-Cookie" ,  cookie.toString()) ; 
        
        HashMap<String,String>  responseBody   = new  HashMap<>() ;  
        responseBody.put("token" ,  accessToken)  ;   
        responseBody.put("email" ,  userRepository.findByUsername(username).getEmail()) ; 
        return  new ResponseEntity<>(    responseBody , headers ,    HttpStatus.OK ) ;
        }
    catch(  BadCredentialsException e  ) { 
       return  new ResponseEntity<>( "Incorrect credentials"  ,  HttpStatus.UNAUTHORIZED   ) ;
    } 

    catch( Exception e ) { 
      return  new ResponseEntity<>( "Internal server error."  ,  HttpStatus.INTERNAL_SERVER_ERROR) ;
    } 
  }
}