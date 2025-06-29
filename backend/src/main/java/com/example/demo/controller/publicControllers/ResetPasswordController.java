package com.example.demo.controller.publicControllers;


import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.EmailTypeEnum;
import com.example.demo.enums.ResponseStatus;
import com.example.demo.model.OTP;
import com.example.demo.model.OTPServiceResult;
import com.example.demo.model.ResetPasswordTokenResult;
import com.example.demo.records.UserDTO;
import com.example.demo.service.JwtService;
import com.example.demo.service.OTPService;
import com.example.demo.service.RabbitMQService;
import com.example.demo.service.UserService;
import com.example.dto.EmailDetails;



@RestController
@RequestMapping("/api/public/resetPassword")
public class ResetPasswordController {   

    @Autowired 
    UserService userService ; 

    @Autowired 
    RabbitMQService rabbitMQService  ; 

    @Autowired 
    OTPService otpService ; 

    @Autowired 
    JwtService jwtService ; 

    @PostMapping("/createOTP") 
    public ResponseEntity<?> createOTP( @RequestParam String username ) {   
        UserDTO user = userService.getUserDTOByUsername(username) ; 
        if(user==null) { return  new ResponseEntity<>("Username does not exist in database." , HttpStatus.BAD_REQUEST) ;  }
        if(user.email()==null) { return  new ResponseEntity<>("The username is not connected with any email address." ,  HttpStatus.BAD_REQUEST  ) ; } 
        OTP otp =  otpService.createOTP(username) ; 
        Long code = otp.getCode() ;
        EmailDetails emailDetails =  new EmailDetails( user.email() ,  "Your one time password for resetting the password is " + code  , "Password reset for YKDevout Exports account." , null) ;  
        rabbitMQService.sendMessage(emailDetails); 
        return new ResponseEntity<>("One-time password sent to the email address " + user.email() , HttpStatus.OK  ) ;  
    }

   @PostMapping("/verifyOTP") 
   public ResponseEntity<?> verifyOTP(@RequestParam Long code ,  UUID id )  {    
    try { 
       OTPServiceResult result =  otpService.verifyOTP(id, code) ;     
       ResponseStatus responseStatus = result.getResponseStatus() ; 
       if(responseStatus == ResponseStatus.INCORREDCT_CREDENTIALS) { 
        int retriesLeft = result.getOtp().getRetriesLeft() ; 
        return new ResponseEntity<>("Incorrect otp. You have " + retriesLeft + " left." , HttpStatus.BAD_REQUEST ) ;  
       }
       if(responseStatus == ResponseStatus.LIMITS_EXCEEDED) { 
        return new ResponseEntity<>("You have exceeded the number of retries."  ,  HttpStatus.BAD_REQUEST  ) ; 
       }
       if(responseStatus == ResponseStatus.NOT_FOUND ) { 
        return new ResponseEntity<>("The uuid of otp sent is not found in the database."  ,  HttpStatus.BAD_REQUEST  ) ; 
       }      
       String token = otpService.afterVerificationMethod(result.getOtp()); 
       HashMap<String,Object>  responseBody  = new HashMap<>() ;  
       responseBody.put("msg", "The otp sent is correct.You will be redirected to reset ur password." ) ; 
       responseBody.put("token" , token   ) ; 
       return  new ResponseEntity<>(  responseBody, HttpStatus.OK ) ;   
    }
    catch( Exception e) { 
        System.out.println(e.getMessage()) ; 
        return new ResponseEntity<>("Internal server error." ,  HttpStatus.INTERNAL_SERVER_ERROR ) ; 
    }
   }

   @PostMapping("/reset") 
   public ResponseEntity<?> resetPassword( @RequestParam String password  ,  @RequestHeader("reset-password-jwtToken")  String jwtToken) {    
    try {   
       ResetPasswordTokenResult resetPasswordTokenResult =  jwtService.verifyResetPasswordToken(jwtToken) ;  
       if(!resetPasswordTokenResult.isStatus()) { 
        return  new ResponseEntity<>("The reset-password token  is tampered." , HttpStatus.UNAUTHORIZED  ) ;  
       }
       UUID id  = resetPasswordTokenResult.getId() ;  
       OTP otp = otpService.getOTP(id) ; 
       if(otp == null) { 
        return  new ResponseEntity<>("The reset-password token  is tampered." , HttpStatus.UNAUTHORIZED  ) ;  
       }
       String username = otp.getUsername() ; 
       boolean   res =  userService.updatePassword(username, password) ; 
       if(!res) {return  new ResponseEntity<>("The reset-password token  is tampered." , HttpStatus.UNAUTHORIZED  ) ;    }
       otpService.deleteOTP(id);
       userService.sendEmail( EmailTypeEnum.PASSWORD_RESET_CONFIRMATION ) ;  
       return new ResponseEntity<>("Password updated successfully." , HttpStatus.OK)  ; 
      } 
    catch(Exception e) {
        System.out.println(e.getMessage()) ; 
        return new ResponseEntity<>("Internal server error." ,  HttpStatus.INTERNAL_SERVER_ERROR ) ; 
    }
   } 


}