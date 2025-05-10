package com.example.demo.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OTP ;
import com.example.demo.repository.OTPRepository;

@Service
public class OTPService {
    @Autowired 
    OTPRepository otpRepository ; 

    public  OTP createOTP() { 
        Random random = new Random() ;
        Long a =   100000L + random.nextInt(999999) ;  
        OTP otp = new OTP(a) ; 
        return otpRepository.save(otp) ;
    }  
    
    public boolean  verifyOTP( Long id ,  Long code ) { 

         OTP  otp_ = otpRepository.findById(id).orElse(null) ; 
         return   otp_!=null && code.equals(otp_.getCode())  ; 
    }

    public void deleteOTP( Long id ) { 
        otpRepository.deleteById(id);
    }
}