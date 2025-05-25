package com.example.demo.model;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthBody { 


    @NotNull
    private String username ; 
    @NotNull
    private String password ; 


}
