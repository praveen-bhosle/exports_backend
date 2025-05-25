package com.example.demo.utils; 

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetUsername  { 
    static public String getUsername()  {  
       Authentication authentication  =   SecurityContextHolder.getContext().getAuthentication() ;        
       if( authentication != null && authentication.isAuthenticated() ) { 
        return  authentication.getName() ; 
       }
        return null ; 
    }
}