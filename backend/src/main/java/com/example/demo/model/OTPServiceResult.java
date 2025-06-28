package com.example.demo.model;

import com.example.demo.enums.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPServiceResult  { 
   private OTP otp ; 
   private ResponseStatus responseStatus  ; 
}