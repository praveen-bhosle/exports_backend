package com.example.demo.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationToken {  
  
   

    @Id
    @GeneratedValue
    private UUID id ;    
    private String  code  ; 
    @OneToOne
    @JoinColumn(name="username", referencedColumnName="username")
    private User  user  ; 
    private String email ;
    
    private Instant expireInstant ; 

    public  EmailVerificationToken( String email   ,  User user  ) {  
        this.email = email ; 
        this.user = user ;
        this.code =   UUID.randomUUID().toString() ;  
        this.expireInstant = Instant.now().plus( 15 , ChronoUnit.MINUTES ) ; 
    } 

    

}