package com.example.demo.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthBody { 


    @NotNull
    private String username ; 
    @NotNull
    private String password ; 


}
