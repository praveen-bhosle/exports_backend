package com.example.demo.service; 
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.model.ResetPasswordTokenResult;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct ;


@Service
public class JwtService { 
    
    @Value("${jwt.secret}") 
    private String secret ;  

    private SecretKey key ; 



@PostConstruct 
public void initKey() { 
    byte[] keyBytes = Decoders.BASE64.decode(secret) ;  
    this.key = Keys.hmacShaKeyFor(keyBytes) ;    
} 

    public  String createToken (String  username ,  Instant time  ) throws  JwtException  {   
       try { 
      return  Jwts.builder().subject(username).expiration( Date.from(time)  ).issuedAt( new Date()).signWith(key).compact() ; } 
      catch( JwtException e ) {  
        System.out.println(e.getMessage()) ; 
        return "Error" ; 
      }
    } 
     
    public  boolean verifyToken( String token  , String username  ) throws JwtException { 
      try {  
      return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject().equals(username) ;  
    } 
      catch( JwtException e ) {  
        System.out.println(e.getMessage()) ; 
        return false; 
      }
    } 
    
    public String  getUsername( String token  )  throws JwtException { 
      try { 
       return  Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject() ;
      } catch (JwtException e) { 
        System.out.println(e.getMessage());
        return  "Error" ;
      }
    } 


    public ResetPasswordTokenResult verifyResetPasswordToken( String token  ) { 
      ResetPasswordTokenResult res = new ResetPasswordTokenResult() ; 
      try {  
         String subject =   Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject() ;   
         UUID  id = UUID.fromString(subject) ; 
         res.setId(id);
         res.setStatus(true);
         return res ; 
      }  
        catch( JwtException | IllegalArgumentException e   ) {  
          System.out.println(e.getMessage()) ;  
          return res; 
        }
     }
}  