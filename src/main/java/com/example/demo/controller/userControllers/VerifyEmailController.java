package com.example.demo.controller.userControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.EmailDetails;
import com.example.demo.model.EmailVerificationToken;
import com.example.demo.model.User;
import com.example.demo.model.VerifyEmailBody;
import com.example.demo.repository.EmailVerificationTokenRepository ;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailServiceImpl;
import com.example.demo.utils.GetUsername;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/user/verifyEmail")
public class VerifyEmailController {   

    @Autowired EmailServiceImpl emailService ; 
    @Autowired EmailVerificationTokenRepository tokenRepository ;
    @Autowired UserRepository userRepository ;


    @PostMapping("")
    public ResponseEntity<?> sendVerificationEmail( @Valid  @RequestBody  VerifyEmailBody   requestBody   ) throws  Exception    {    
    
        try {   
        String username = GetUsername.getUsername() ;
        String email = requestBody.getEmail() ;
        User newUser = userRepository.findByUsername(username) ; 
        EmailVerificationToken token  = new   EmailVerificationToken( email ,  newUser   ) ;   
        tokenRepository.save(token) ; 
        String code = token.getCode()  ;
        String url =  "http://13.61.25.227:5000/api/user/verifyEmail/" + code  ; 
        EmailDetails emailDetails = new EmailDetails(  email ,  "Click on this link to verify your email id. " +  url  , "Verification for your YKDevoutExports account."  ,  null  ) ;   
        String  a = emailService.sendSimpleMail(emailDetails) ;   
        if(a.equals("Mail Sent Successfully")) { 
        return  new ResponseEntity<>("We have sent an verifcation link to " + email  ,  HttpStatus.OK   ) ;  } 
        throw new Exception("Error sending message") ;
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
        Long id = token.getId() ;  

        if(usernameFromToken.equals(username) ) {  

           User user = userRepository.findByUsername(username) ;
           user.setEmail(token.getEmail());
           userRepository.save(user) ;
           tokenRepository.deleteById(id);
           return  new ResponseEntity<>("Email verified successfully." ,  HttpStatus.CREATED)  ; 
        } 

        return  new ResponseEntity<>( HttpStatus.UNAUTHORIZED ) ; 
        } 
        catch( NumberFormatException | NullPointerException e  ) { 
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;
        } 
        catch( Exception e  ) { 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ; 
        }
    } 
}