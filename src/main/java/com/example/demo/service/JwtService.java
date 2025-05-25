package com.example.demo.service; 
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.utils.time;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders ;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;


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

    public  String createToken (String  username ) throws  JwtException  {   
       try { 
      return  Jwts.builder().subject(username).expiration(time.afterOneMonth()).issuedAt( time.current()).signWith(key).compact() ; } 
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
}  