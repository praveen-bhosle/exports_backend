package com.example.demo.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.OTPStatus;
import com.example.demo.enums.ResponseStatus;
import com.example.demo.model.OTP;
import com.example.demo.model.OTPServiceResult;
import com.example.demo.repository.OTPRepository;

@Service
public class OTPService {
    @Autowired 
    OTPRepository otpRepository ; 

    @Autowired 
    JwtService jwtService ; 

    public  OTP createOTP(String username ) { 
        Random random = new Random() ;
        Long code =   100000L + random.nextInt(999999) ;  
        OTP otp = new OTP(code,username) ; 
        return otpRepository.save(otp) ;
    }  
    
    public OTPServiceResult verifyOTP( UUID id ,  Long code ) { 
        OTPServiceResult result = new OTPServiceResult() ;
       
        OTP  otp = otpRepository.findById(id).orElse(null) ;      
        if(otp == null) {  
            result.setResponseStatus( ResponseStatus.NOT_FOUND  );  
            return  result ; 
        }   
        if( otp.getRetriesLeft() <= 0  ) { 
            result.setResponseStatus(ResponseStatus.LIMITS_EXCEEDED); 
            return result ;  
        }  
        if(!code.equals(otp.getCode())) {
            otp.decreaseRetries(); 
            result.setResponseStatus( ResponseStatus.INCORREDCT_CREDENTIALS ); 
            return result ; 
        }
        result.setOtp(otp);
        result.setResponseStatus(ResponseStatus.OK);
        return result ; 
    }

    public String  afterVerificationMethod(OTP otp) { 
        otp.setStatus(OTPStatus.VERIFIED);
        otpRepository.save(otp) ; 
        UUID id  = otp.getId() ; 
        String token  = jwtService.createToken(id.toString() ,  Instant.now().plus(5,ChronoUnit.MINUTES) )  ;  
        return token  ;
    }

    public OTP getOTP( UUID id  ) { 
        return  otpRepository.findById(id).orElse(null);  
    }

    public void deleteOTP( UUID id ) { 
        otpRepository.deleteById(id);
    }
}