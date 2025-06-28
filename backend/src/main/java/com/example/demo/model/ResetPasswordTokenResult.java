package com.example.demo.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordTokenResult {
   private  boolean  status = false   ; 
   private  UUID     id   ; 
}