package com.example.demo.controller.userControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.OTP;
import com.example.demo.service.OTPService;


@RestController
@RequestMapping("/api/user/otp")
public class OTPController {

    @Autowired 
    OTPService otpService ; 
  

    @PostMapping("/create") 
    public OTP createOTP(  ) { 
        return  otpService.createOTP() ;
    } 

    @GetMapping("/verify") 
    public boolean  verifyOTP( @RequestParam  Long id  , @RequestParam Long code   ) { 

        return  otpService.verifyOTP(id, code) ;
    } 

    @DeleteMapping("/delete") 
    public void deleteOTP(@RequestParam Long id ) { 
         otpService.deleteOTP(id) ;
    }
    
}