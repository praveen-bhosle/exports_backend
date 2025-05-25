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

@RestController 
@RequestMapping("/user/verify-email")
public class VerifyEmailController {   

    @Autowired EmailServiceImpl emailService ; 
    @Autowired EmailVerificationTokenRepository tokenRepository ; 
    String username = GetUsername.getUsername() ;
    @Autowired UserRepository userRepository ;


    @PostMapping("")
    public ResponseEntity<?> sendVerificationEmail(  @RequestBody  VerifyEmailBody   requestBody   )   {      

        try {   
        String email = requestBody.getEmail() ;
        EmailVerificationToken token  = new   EmailVerificationToken( email ,  username  ) ;   
        String code = token.getCode() + token.getId().toString() ; 
        String url =  "www.ykdevoutexports.com/api/user/verify-email/" + code  ; 
        tokenRepository.save(token) ; 
        EmailDetails emailDetails = new EmailDetails(  email ,  "<p>Click on this link to verify your email id.</p><br/> <a href=\" "+  url + "\">"  +  url + "</a>" , "Verification for your YKDevoutExports account."  ,  null  ) ;   
        emailService.sendSimpleMail(emailDetails) ;   
        return  new ResponseEntity<>("We have sent an verifcation link to " + email  ,  HttpStatus.ACCEPTED   ) ; 
    } 

        catch( Exception e  ) { 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ; 
         }
    } 

    @GetMapping("/{url}")
    public ResponseEntity<?>  checkVerificationUrl(@PathVariable String url) {  
        try{ 
        int size = url.length() ;
        Long id = Long.valueOf(url.substring(size-2)) ;
        String code = url.substring(0,size-2) ; 

        EmailVerificationToken token  = tokenRepository.findById(id).orElse(null) ; 
        String  usernameFromToken = token.getUser().getUsername() ;   
        String codeFromToken = token.getCode() ;

        if(usernameFromToken.equals(username) && code.equals(codeFromToken)) {  

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