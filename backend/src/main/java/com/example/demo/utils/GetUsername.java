package com.example.demo.utils; 

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetUsername  { 
    static public String getUsername()  {  
       Authentication authentication  =   SecurityContextHolder.getContext().getAuthentication() ;    
       System.out.println(authentication) ;     
       if( authentication != null && authentication.isAuthenticated() ) {  
        System.out.println(authentication) ; 
        return  authentication.getName() ; 
       }
        return null ; 
    }
} 