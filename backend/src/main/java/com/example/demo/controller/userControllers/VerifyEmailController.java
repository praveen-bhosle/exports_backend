package com.example.demo.controller.userControllers;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.EmailVerificationToken;
import com.example.demo.model.User;
import com.example.demo.model.VerifyEmailBody;
import com.example.demo.repository.EmailVerificationTokenRepository;
import com.example.demo.repository.UserRepository ;
import com.example.demo.service.RabbitMQService;
import com.example.demo.utils.GetUsername;
import com.example.dto.EmailDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/user/verifyEmail")
public class VerifyEmailController {   

  
    @Autowired EmailVerificationTokenRepository tokenRepository ;
    @Autowired UserRepository userRepository ;

    @Autowired RabbitMQService rabbitMQService ;   





    @PostMapping("")
    public ResponseEntity<?> sendVerificationEmail( @Valid  @RequestBody  VerifyEmailBody   requestBody   ) throws  Exception    {    
    
        try {   
        String username = GetUsername.getUsername() ;
        String email = requestBody.getEmail() ;
        User newUser = userRepository.findByUsername(username) ; 
        EmailVerificationToken token  = new   EmailVerificationToken( email ,  newUser   ) ;   
        tokenRepository.save(token) ; 
        String code = token.getCode()  ;
        String url = "https://fancy-cendol-1295ff.netlify.app/auth/verifyEmail2?code=" + code  ; 
        EmailDetails emailDetails = new EmailDetails(  email ,  "Click on this link to verify your email id. " +  url  , "Verification for your YKDevoutExports account."  ,  null  ) ;   
        ObjectMapper objectMapper = new ObjectMapper() ; 
        String json = objectMapper.writeValueAsString(emailDetails) ; 
        rabbitMQService.sendMessage(json); 
        return  new ResponseEntity<>("We have sent an verifcation link to " + email  ,  HttpStatus.OK   ) ;  
    } 

        catch( Exception e  ) {  
            System.err.println(e.getMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ; 
         }
    } 

    @GetMapping("/{url}")
    public ResponseEntity<?>  checkVerificationUrl(@PathVariable String url) {  
        try{ 

        String username = GetUsername.getUsername()  ; 
        String code = url ;  
        EmailVerificationToken token  = tokenRepository.findByCode(code) ; 
        String  usernameFromToken = token.getUser().getUsername() ; 
        UUID id = token.getId() ;  

        if(!Instant.now().isBefore(token.getExpireInstant()))  { 
            return new ResponseEntity<>( "Link expired." , HttpStatus.BAD_REQUEST ) ; 
         }

        if(usernameFromToken.equals(username )) {  
           User user = userRepository.findByUsername(username) ;
           user.setEmail(token.getEmail());
           userRepository.save(user) ;
           tokenRepository.deleteById(id);
           return  new ResponseEntity<>("Email verified successfully." ,  HttpStatus.OK)  ; 
        } 

        return  new ResponseEntity<>( "Not authorized." ,  HttpStatus.UNAUTHORIZED ) ; 
        } 
        catch( NumberFormatException | NullPointerException e  ) { 
            return  new ResponseEntity<>( "Bad request" ,  HttpStatus.BAD_REQUEST) ;
        } 
        catch( Exception e  ) { 
            return new ResponseEntity<>( "Internal server error" ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
        }
    } 
}