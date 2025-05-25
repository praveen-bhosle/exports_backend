package com.example.demo.model;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {
    private String receiver ; 
    private String text ; 
    private String subject; 
    @Nullable
    private String attachment ;
}