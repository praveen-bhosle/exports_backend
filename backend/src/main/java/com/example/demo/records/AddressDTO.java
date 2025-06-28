package com.example.demo.records;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record  AddressDTO ( 
    UUID id , 
    @NotNull
    String fullName ,
    @NotNull 
    String mobileNumber ,
    @NotNull
    String pincode , 
    String addr1  ,
    String addr2 ,
    String landmark ,
    @NotNull 
    String city , 
    @NotNull
    String state ,  
    @NotNull
    String country , 
    @NotNull
    Boolean isDefault  
    )
    {} 